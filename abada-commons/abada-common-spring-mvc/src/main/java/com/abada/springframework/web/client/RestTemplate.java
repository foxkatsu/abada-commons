/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.springframework.web.client;

/*
 * #%L
 * Abada Commons
 * %%
 * Copyright (C) 2013 Abada Servicios Desarrollo (investigacion@abadasoft.com)
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the 
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public 
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

import com.abada.springframework.http.converter.json.JsonHttpMessageConverter;
import com.abada.springframework.security.web.authentication.preauth.x509.X509AuthenticationFilter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.util.UriTemplate;
import org.springframework.web.util.UriUtils;

/**
 * Exttends from RestTemplate. Add custom methods
 *
 * @author katsu
 * @author miguel
 */
public class RestTemplate extends org.springframework.web.client.RestTemplate {

    private DefaultHttpClient httpClient;
    private static final String REST_COOKIE = "restTemplateCookie";
    private HttpSession session;
    private boolean ignoreSession;

    public RestTemplate() {
        super();
        httpClient = new DefaultHttpClient();
    }

    private void setRequestFactoryPriv(HttpServletRequest request, boolean ignoreSession, String username, String password, boolean byCertificate) throws Exception {
        if (!byCertificate) {
            this.setRequestFactoryPrivBasicAuth(request, ignoreSession, username, password);
        } else {
            this.setRequestFactoryPrivCertificationAuth(request, ignoreSession, username);
        }
    }

    private void setRequestFactoryPrivCertificationAuth(HttpServletRequest request, boolean ignoreSession, String username) throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException, UnrecoverableKeyException, CertificateException, IOException, Exception {
        this.ignoreSession = ignoreSession;
        if (username != null) {
            HttpSession tSession;
            if (request != null) {
                tSession = request.getSession();
            } else {
                tSession = null;
            }
            if (ignoreSession || tSession.getAttribute(REST_COOKIE) == null) {                
                //Secure Protocol implementation.                
                SSLContext ctx = SSLContext.getInstance("TLS");
                MyX509TrustManager myX509TrustManager = new MyX509TrustManager();
                myX509TrustManager.setUserCertificate((X509Certificate) tSession.getAttribute(X509AuthenticationFilter.USER_CERTIFICATE_COOKIE));                                                
                
                ctx.init(myX509TrustManager.getKmf().getKeyManagers(), new TrustManager[]{new MyX509TrustManager()}, null);

                SSLSocketFactory ssf = new SSLSocketFactory(ctx);
                
                ClientConnectionManager ccm = httpClient.getConnectionManager();
                //register https protocol in httpclient's scheme registry
                SchemeRegistry sr = ccm.getSchemeRegistry();
                sr.register(new Scheme("https", 443, ssf)); 
                
                httpClient=new DefaultHttpClient(ccm);
            } else {
                for (Cookie cookie : (List<Cookie>) tSession.getAttribute(REST_COOKIE)) {
                    httpClient.getCookieStore().addCookie(cookie);
                }
            }
            ClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
            this.setRequestFactory(clientHttpRequestFactory);
            if (!ignoreSession) {
                session = tSession;
            }
        } else {
            throw new Exception("Session Credentials incorrect type.");
        }
    }

    private void setRequestFactoryPrivBasicAuth(HttpServletRequest request, boolean ignoreSession, String username, String password) throws Exception {
        this.ignoreSession = ignoreSession;
        if (username != null) {
            HttpSession tSession;
            if (request != null) {
                tSession = request.getSession();
            } else {
                tSession = null;
            }
            if (ignoreSession || tSession == null || tSession.getAttribute(REST_COOKIE) == null) {
                httpClient.getCredentialsProvider().setCredentials(
                        new AuthScope(AuthScope.ANY),
                        new UsernamePasswordCredentials(username, password));
            } else {
                for (Cookie cookie : (List<Cookie>) tSession.getAttribute(REST_COOKIE)) {
                    httpClient.getCookieStore().addCookie(cookie);
                }
            }
            ClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
            this.setRequestFactory(clientHttpRequestFactory);
            if (!ignoreSession) {
                session = tSession;
            }
        } else {
            throw new Exception("Session Credentials incorrect type.");
        }
    }

    public void setRequestFactory(HttpServletRequest request, boolean ignoreSession) throws Exception {
        Principal up = request.getUserPrincipal();
        if (up instanceof UsernamePasswordAuthenticationToken) {
            UsernamePasswordAuthenticationToken upat = (UsernamePasswordAuthenticationToken) up;
            this.setRequestFactoryPriv(request, ignoreSession, upat.getName().toString(), upat.getCredentials().toString(), false);
        } else if (up instanceof PreAuthenticatedAuthenticationToken) {
            PreAuthenticatedAuthenticationToken upat = (PreAuthenticatedAuthenticationToken) up;
            this.setRequestFactoryPriv(request, ignoreSession, upat.getName().toString(), null, true);
        }
    }

    public void setRequestFactory(String user, String password) throws Exception {
        this.setRequestFactory(null, true, user, password);
    }

    public void setRequestFactory(HttpServletRequest request, boolean ignoreSession, String user, String password) throws Exception {
        this.setRequestFactoryPriv(request, ignoreSession, user, password, false);
    }

    public void setRequestFactory(HttpServletRequest request, String user, String password) throws Exception {
        this.setRequestFactory(request, false, user, password);
    }

    public void setRequestFactory(HttpServletRequest request) throws Exception {
        this.setRequestFactory(request, false);
    }

    /**
     * Get file in file
     *
     * @param url
     * @param file
     */
    public File getForFile(String url) {
        return (File) execute(url, HttpMethod.GET, null, new FileResponseExtractor());
    }

    public String postMultipart(String url, Map<String, Object> params) throws Exception {

        MultipartEntity multipartEntity = new MultipartEntity();

        for (String k : params.keySet()) {
            Object aux = params.get(k);
            if (aux instanceof File) {
                multipartEntity.addPart("bin", (FileBody) aux);
            } else {
                multipartEntity.addPart("comment", (StringBody) aux);
            }
        }

        /*   List<Part> parts = new ArrayList<Part>();
         for (String k : params.keySet()) {
         Object aux = params.get(k);
         if (aux instanceof File) {
         parts.add(new FilePart(k, (File) aux));
         } else {
         parts.add(new StringPart(k, aux.toString()));
         }
         }*/

        HttpPost postMethod = new HttpPost(url);
        HttpResponse httpResponse = this.httpClient.execute(postMethod);
        try {
            postMethod.setEntity(multipartEntity);

            int status = httpResponse.getStatusLine().getStatusCode();
            switch (status) {
                case 401:
                    break;
                case 200:
                    if (!ignoreSession && session != null) {
                        session.setAttribute(REST_COOKIE, this.httpClient.getCookieStore().getCookies());
                    }
                    return httpResponse.getStatusLine().toString();
                default:
                    break;
            }
        } finally {
            httpClient.getConnectionManager().shutdown();
        }
        return null;
    }

    public byte[] getForBytes(String url) {
        return (byte[]) execute(url, HttpMethod.GET, null, new ByteResponseExtractor());
    }

    public <T> T postForObject(URI url, Map<String, Object> postParameters, Class<T> responseType) throws RestClientException {
        AddParamsRequestCallback requestCallback = new AddParamsRequestCallback(postParameters, "application/x-www-form-urlencoded", HttpMethod.POST);
        //HttpEntityRequestCallback requestCallback = new HttpEntityRequestCallback(request, responseType);
        HttpMessageConverterExtractor<T> responseExtractor =
                new HttpMessageConverterExtractor<T>(responseType, getMessageConverters());
        return execute(url, HttpMethod.POST, requestCallback, responseExtractor);
    }

    public byte[] post(String url, Map<String, Object> params) throws UnsupportedEncodingException {
        AddParamsRequestCallback requestCb = new AddParamsRequestCallback(params, "application/x-www-form-urlencoded", HttpMethod.POST);
        return (byte[]) execute(url, HttpMethod.POST, requestCb, new ByteResponseExtractor());
    }

    public <T> T put(String url, Class<T> responseType, Object request, Object... urlVariables) throws RestClientException {
        HttpEntityRequestCallback requestCallback = new HttpEntityRequestCallback(request);
        UriTemplate uriTemplate = new HttpUrlTemplate(url);
        URI uri = uriTemplate.expand(urlVariables);
        HttpMessageConverterExtractor<T> responseExtractor =
                new HttpMessageConverterExtractor<T>(responseType, getMessageConverters());
        return execute(uri, HttpMethod.PUT, requestCallback, responseExtractor);
    }

    public <T> T delete(String url, Class<T> responseType, Object... urlVariables) throws RestClientException {
        UriTemplate uriTemplate = new HttpUrlTemplate(url);
        URI uri = uriTemplate.expand(urlVariables);
        HttpMessageConverterExtractor<T> responseExtractor =
                new HttpMessageConverterExtractor<T>(responseType, getMessageConverters());
        return execute(uri, HttpMethod.DELETE, null, responseExtractor);
    }

    public Object getForObjectJson(String url, Type responseType) throws RestClientException {
        AcceptHeaderJsonRequestCallback requestCallback = new AcceptHeaderJsonRequestCallback();
        JsonResponseExtractor responseExtractor = new JsonResponseExtractor(responseType, this.getMessageConverters());
        return execute(url, HttpMethod.GET, requestCallback, responseExtractor);
    }

    @Override
    protected <T> T doExecute(URI url, HttpMethod method, RequestCallback requestCallback, ResponseExtractor<T> responseExtractor) throws RestClientException {
        T result = super.doExecute(url, method, requestCallback, responseExtractor);
        if (!ignoreSession && session != null) {
            session.setAttribute(REST_COOKIE, this.httpClient.getCookieStore().getCookies());
        }
        return result;
    }

    //<editor-fold defaultstate="collapsed" desc="Class from parent because are private bitch">
    /**
     * Request callback implementation that prepares the request's accept
     * headers.
     */
    private class AcceptHeaderJsonRequestCallback implements RequestCallback {

        @SuppressWarnings("unchecked")
        @Override
        public void doWithRequest(ClientHttpRequest request) throws IOException {
            request.getHeaders().setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        }
    }

    /**
     * Response extractor for {@link HttpEntity}.
     */
    class JsonResponseExtractor implements ResponseExtractor {

        private Type responseType;
        private List<HttpMessageConverter<?>> messageConverters;

        public JsonResponseExtractor(Type responseType, List<HttpMessageConverter<?>> messageConverters) {
            this.responseType = responseType;
            this.messageConverters = messageConverters;
        }

        @Override
        public Object extractData(ClientHttpResponse response) throws IOException {
            JsonHttpMessageConverter converter = this.getJsonHttpMessageConverter();
            if (converter != null) {
                return converter.deserialize(response.getBody(), responseType);
            } else {
                throw new RestClientException("No JsonHttpMessageConverter detected");
            }
        }

        private JsonHttpMessageConverter getJsonHttpMessageConverter() {
            if (messageConverters != null) {
                for (HttpMessageConverter hmc : messageConverters) {
                    if (hmc instanceof JsonHttpMessageConverter) {
                        return (JsonHttpMessageConverter) hmc;
                    }
                }
            }
            return null;
        }
    }

    class AddParamsRequestCallback extends AcceptHeaderRequestCallback {

        private static final String CONTENT_TYPE = "Content-Type";
        private Map<String, Object> params;
        private HttpMethod method;
        private String contentType;

        public AddParamsRequestCallback(Map<String, Object> params, String contentType, HttpMethod method) {
            super(null);
            this.params = params;
            this.method = method;
            this.contentType = contentType;
        }

        @Override
        public void doWithRequest(ClientHttpRequest request) throws IOException {
            super.doWithRequest(request);
            request.getHeaders().remove(CONTENT_TYPE);
            request.getHeaders().add(CONTENT_TYPE, contentType);
            OutputStreamWriter ow = new OutputStreamWriter(request.getBody());
            try {
                String write = com.abada.web.util.URL.paramUrlGrid(params, method);
                ow.write(write);
            } catch (Exception e) {
                logger.error(e);
            } finally {
                ow.flush();
            }
        }
    }

    class FileResponseExtractor implements ResponseExtractor {

        @Override
        public Object extractData(ClientHttpResponse response) throws IOException {
            //File Name
            StringBuilder fileName = new StringBuilder();
            if (response.getHeaders().get("Content-Disposition") != null && response.getHeaders().get("Content-Disposition").size() > 0) {
                for (String s : response.getHeaders().get("Content-Disposition")) {
                    fileName.append(s);
                }
                int i = fileName.indexOf("filename");
                i = fileName.indexOf("\"", i);
                int i2 = fileName.indexOf("\"", i + 1);
                fileName = new StringBuilder(fileName.substring(i + 1, i2));
            }

            fileName.insert(0, UUID.randomUUID().toString());
            File temp = new File(System.getProperty("java.io.tmpdir") + "/" + fileName.toString());
            if (temp.exists()) {
                temp.delete();
            }

            byte[] read = new byte[1024];
            int r;
            FileOutputStream bos = new FileOutputStream(temp);

            while ((r = response.getBody().read(read)) != -1) {
                bos.write(read, 0, r);
            }

            bos.close();
            return temp;
        }
    }

    class ByteResponseExtractor implements ResponseExtractor {

        @Override
        public Object extractData(ClientHttpResponse response) throws IOException {
            byte[] read = new byte[1024];
            int r;
            ByteArrayOutputStream bos = new ByteArrayOutputStream();

            while ((r = response.getBody().read(read)) != -1) {
                bos.write(read, 0, r);
            }

            return bos.toByteArray();
        }
    }

    /**
     * HTTP-specific subclass of UriTemplate, overriding the encode method.
     */
    class HttpUrlTemplate extends UriTemplate {

        public HttpUrlTemplate(String uriTemplate) {
            super(uriTemplate);
        }

        @Override
        protected URI encodeUri(String uri) {
            try {
                String encoded = UriUtils.encodeHttpUrl(uri, "UTF-8");
                return new URI(encoded);
            } catch (UnsupportedEncodingException ex) {
                // should not happen, UTF-8 is always supported
                throw new IllegalStateException(ex);
            } catch (URISyntaxException ex) {
                throw new IllegalArgumentException("Could not create HTTP URL from [" + uri + "]: " + ex, ex);
            }
        }
    }

    /**
     * Request callback implementation that writes the given object to the
     * request stream.
     */
    class HttpEntityRequestCallback extends AcceptHeaderRequestCallback {

        private final HttpEntity requestEntity;

        private HttpEntityRequestCallback(Object requestBody) {
            this(requestBody, null);
        }

        @SuppressWarnings("unchecked")
        private HttpEntityRequestCallback(Object requestBody, Class<?> responseType) {
            super(responseType);
            if (requestBody instanceof HttpEntity) {
                this.requestEntity = (HttpEntity) requestBody;
            } else if (requestBody != null) {
                this.requestEntity = new HttpEntity(requestBody);
            } else {
                this.requestEntity = HttpEntity.EMPTY;
            }
        }

        @Override
        @SuppressWarnings("unchecked")
        public void doWithRequest(ClientHttpRequest httpRequest) throws IOException {
            super.doWithRequest(httpRequest);
            if (!requestEntity.hasBody()) {
                HttpHeaders httpHeaders = httpRequest.getHeaders();
                HttpHeaders requestHeaders = requestEntity.getHeaders();
                if (!requestHeaders.isEmpty()) {
                    httpHeaders.putAll(requestHeaders);
                }
                if (httpHeaders.getContentLength() == -1) {
                    httpHeaders.setContentLength(0L);
                }
            } else {
                Object requestBody = requestEntity.getBody();
                Class<?> requestType = requestBody.getClass();
                HttpHeaders requestHeaders = requestEntity.getHeaders();
                MediaType requestContentType = requestHeaders.getContentType();
                for (HttpMessageConverter messageConverter : getMessageConverters()) {
                    if (messageConverter.canWrite(requestType, requestContentType)) {
                        if (!requestHeaders.isEmpty()) {
                            httpRequest.getHeaders().putAll(requestHeaders);
                        }
                        if (logger.isDebugEnabled()) {
                            if (requestContentType != null) {
                                logger.debug("Writing [" + requestBody + "] as \"" + requestContentType
                                        + "\" using [" + messageConverter + "]");
                            } else {
                                logger.debug("Writing [" + requestBody + "] using [" + messageConverter + "]");
                            }

                        }
                        messageConverter.write(requestBody, requestContentType, httpRequest);
                        return;
                    }
                }
                String message = "Could not write request: no suitable HttpMessageConverter found for request type ["
                        + requestType.getName() + "]";
                if (requestContentType != null) {
                    message += " and content type [" + requestContentType + "]";
                }
                throw new RestClientException(message);
            }
        }
    }

    class AcceptHeaderRequestCallback implements RequestCallback {

        private final Class<?> responseType;

        private AcceptHeaderRequestCallback(Class<?> responseType) {
            this.responseType = responseType;
        }

        @SuppressWarnings("unchecked")
        @Override
        public void doWithRequest(ClientHttpRequest request) throws IOException {
            if (responseType != null) {
                List<MediaType> allSupportedMediaTypes = new ArrayList<MediaType>();
                for (HttpMessageConverter<?> messageConverter : getMessageConverters()) {
                    if (messageConverter.canRead(responseType, null)) {
                        List<MediaType> supportedMediaTypes = messageConverter.getSupportedMediaTypes();
                        for (MediaType supportedMediaType : supportedMediaTypes) {
                            if (supportedMediaType.getCharSet() != null) {
                                supportedMediaType =
                                        new MediaType(supportedMediaType.getType(), supportedMediaType.getSubtype());
                            }
                            allSupportedMediaTypes.add(supportedMediaType);
                        }
                    }
                }
                if (!allSupportedMediaTypes.isEmpty()) {
                    MediaType.sortBySpecificity(allSupportedMediaTypes);
                    if (logger.isDebugEnabled()) {
                        logger.debug("Setting request Accept header to " + allSupportedMediaTypes);
                    }
                    request.getHeaders().setAccept(allSupportedMediaTypes);
                }
            }
        }
    }
    //</editor-fold>

    private class MyX509TrustManager implements X509TrustManager {

        private X509Certificate userCertificate;

        private X509TrustManager pkixTrustManager;
        
        private KeyManagerFactory kmf;
        
        private KeyStore keyStore;

        public X509Certificate getUserCertificate() {
            return userCertificate;
        }

        public void setUserCertificate(X509Certificate userCertificate) {
            this.userCertificate = userCertificate;
        }

        public KeyManagerFactory getKmf() {
            return kmf;
        }

        public void setKmf(KeyManagerFactory kmf) {
            this.kmf = kmf;
        }        

        public KeyStore getKeyStore() {
            return keyStore;
        }

        public void setKeyStore(KeyStore keyStore) {
            this.keyStore = keyStore;
        }
        
        MyX509TrustManager() throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException, UnrecoverableKeyException {

            keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null, "".toCharArray());
            
            kmf = KeyManagerFactory.getInstance("SunX509");
            keyStore.setCertificateEntry("alias", userCertificate); //X509Certificate obtained from windows keystore
            kmf.init(keyStore, "".toCharArray());                        

            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("PKIX");
            trustManagerFactory.init(keyStore);

            TrustManager trustManagers[] = trustManagerFactory.getTrustManagers();

            for (TrustManager trustManager : trustManagers) {
                if (trustManager instanceof X509TrustManager) {
                    pkixTrustManager = (X509TrustManager) trustManager;
                    return;
                }
            }

            throw new CertificateException("Couldn't initialize");
        }

        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            pkixTrustManager.checkServerTrusted(chain, authType);
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            //pkixTrustManager.checkServerTrusted(chain, authType);
        }

        public X509Certificate[] getAcceptedIssuers() {
            return pkixTrustManager.getAcceptedIssuers();
        }
    }
}
