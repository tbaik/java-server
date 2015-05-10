package com.tony.server;

import com.tony.server.response.FileContentResponse;
import com.tony.server.response.FileTest;
import com.tony.server.response.FourOhFourResponse;
import com.tony.server.response.MethodNotAllowedResponse;
import com.tony.server.response.ParameterDecodeResponse;
import com.tony.server.response.PatchResponse;
import com.tony.server.response.PreconditionFailedResponse;
import com.tony.server.response.Response;
import com.tony.server.response.UnauthorizedResponse;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ResponseDeterminerTest extends FileTest{
    private ResponseDeterminer responseDeterminer;
    private Router router;
    private ArrayList<String> uriList;
    private Authenticator authenticator;

    @Before
    public void setUp() throws Exception {
        uriList = new ArrayList<>();
        authenticator = new Authenticator(new Logger());
        router = new Router();
        responseDeterminer = new ResponseDeterminer(router, uriList, authenticator);
    }

    private Request getPreconditionFailingRequest() {
        Request request = new Request("PATCH", "/something");
        request.addToHeaders("If-Match", "etag");
        router.addRoute(new Request("PATCH", "/something"), new PatchResponse(""));
        return request;
    }

    private Request getUnauthorizedUserRequest() {
        Request request = new Request("GET", "/file_needs_authorization");
        router.addRoute(request, new FourOhFourResponse());

        authenticator.addToAuthenticatedUsers("user:notAuthorized");
        authenticator.addToAuthenticationList(request);
        return request;
    }

    private Request getMatchingEtagRequest(String filePath) {
        String normalTextAsEtag = "1e63fd95c77abc6e56efcc92065f3de9cbcc0941";
        Request request = new Request("PATCH", "/something");
        request.addToHeaders("If-Match", normalTextAsEtag);

        router.addRoute(request, new PatchResponse(filePath));

        writeTestFile(filePath, "normal text");
        return request;
    }

    @Test
    public void testDetermineResponseReturnsResponseIfInRouter() throws Exception {
        String filePath = "some_path/form";
        Request getRequest = new Request("GET", "/form");
        router.addRoute(getRequest, new FileContentResponse(filePath));

        assertEquals(new FileContentResponse("/pathsomewhere").getClass(),
                responseDeterminer.determineResponse(getRequest).getClass());
    }

    @Test
    public void testDetermineResponseReturnsMethodNotAllowedIfNotInRouterButInURIList() throws Exception {
        uriList.add("/text-file.txt");
        uriList.add("/file1");

        Request putRequest = new Request("PUT", "/file1");
        Request postRequest = new Request("POST", "/text-file.txt");

        assertEquals(new MethodNotAllowedResponse().getClass(),
                responseDeterminer.determineResponse(putRequest).getClass());
        assertEquals(new MethodNotAllowedResponse().getClass(),
                responseDeterminer.determineResponse(postRequest).getClass());
    }

    @Test
    public void testDetermineResponseReturnsFourOhFourWhenNotFindingRouterNorURI() throws Exception {
        Request putRequest = new Request("PUT", "/file1");

        assertEquals(new FourOhFourResponse().getClass(),
                responseDeterminer.determineResponse(putRequest).getClass());
    }

    @Test
    public void testFourOhOneResponseOnRequiredAuthenticationButIsNotAuthenticated() throws Exception {
        Request request = getUnauthorizedUserRequest();

        assertEquals(new UnauthorizedResponse().getClass(),
                responseDeterminer.determineResponse(request).getClass());
    }

    @Test
    public void testParameterDecodeResponseOnParameterURI() throws Exception {
        Request request = new Request("GET", "/parameters?blah");

        assertEquals(ParameterDecodeResponse.class, responseDeterminer.determineResponse(request).getClass());
    }

    @Test
    public void testPatchReturnsPatchResponseOnMatchingEtag() throws Exception {
        String filePath = System.getProperty("user.dir") + "/something";
        Request request = getMatchingEtagRequest(filePath);

        assertEquals(PatchResponse.class, responseDeterminer.determineResponse(request).getClass());
        deleteTestFile(filePath);
    }

    @Test
    public void testPatchReturnsPreconditionFailedResponseOnWrongEtag() throws Exception {
        Request request = getPreconditionFailingRequest();

        assertEquals(PreconditionFailedResponse.class, responseDeterminer.determineResponse(request).getClass());
    }

    @Test
    public void testEtagMatchesCurrentFileEtag() throws Exception {
        String filePath = System.getProperty("user.dir") + "/testEtag";
        writeTestFile(filePath, "normal text");

        String normalTextAsEtag = "1e63fd95c77abc6e56efcc92065f3de9cbcc0941";

        assertTrue(responseDeterminer.fileEtagMatchesGivenEtag(filePath, normalTextAsEtag));
        deleteTestFile(filePath);
    }

    @Test
    public void testHandleRoutedResponseWithNormalGETRoute() throws Exception {
        Request request = new Request("GET", "/existing_route");
        router.addRoute(request, new FileContentResponse("/path/to/existing_route"));
        Response response = responseDeterminer.handleRoutedResponse(request);

        assertEquals(FileContentResponse.class, response.getClass());
    }

    @Test
    public void testHandleRoutedResponseWithUnauthorizedUser() throws Exception {
        Request request = getUnauthorizedUserRequest();

        Response response = responseDeterminer.handleRoutedResponse(request);

        assertEquals(UnauthorizedResponse.class, response.getClass());
    }


    @Test
    public void testHandleRoutedResponseWithPreconditionFailingRequest() throws Exception {
        Request request = getPreconditionFailingRequest();
        Response response = responseDeterminer.handleRoutedResponse(request);

        assertEquals(PreconditionFailedResponse.class, response.getClass());
    }

    @Test
    public void testHandleRoutedResponseWithMatchingEtags() throws Exception {
        String filePath = System.getProperty("user.dir") + "/something";
        Request request = getMatchingEtagRequest(filePath);
        Response response = responseDeterminer.handleRoutedResponse(request);

        assertEquals(PatchResponse.class, response.getClass());
        deleteTestFile(filePath);
    }

    @Test
    public void testHandlePatchResponseWithPreconditionFailingRequest() throws Exception {
        Request request = getPreconditionFailingRequest();
        Response response = responseDeterminer.handlePatchResponse(request);

        assertEquals(PreconditionFailedResponse.class, response.getClass());
    }

    @Test
    public void testHandlePatchResponseWithMatchingEtags() throws Exception {
        String filePath = System.getProperty("user.dir") + "/something";
        Request request = getMatchingEtagRequest(filePath);
        Response response = responseDeterminer.handlePatchResponse(request);

        assertEquals(PatchResponse.class, response.getClass());
        deleteTestFile(filePath);
    }
}