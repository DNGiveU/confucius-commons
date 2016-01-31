/**
 * AliExpress.com. Copyright (c) 2010-2015 All Rights Reserved.
 */
package org.confucius.commons.lang.net;

import com.google.common.collect.Maps;
import junit.framework.Assert;
import org.confucius.commons.lang.ClassLoaderUtil;
import org.junit.Test;

import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * {@link URLUtil} Test
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @version 1.0.0
 * @see URLUtilTest
 * @since 1.0.0
 */
public class URLUtilTest {

    @Test
    public void testEncodeAndDecode() {
        String path = "/abc/def";

        String encodedPath = URLUtil.encode(path);
        String decodedPath = URLUtil.decode(encodedPath);
        Assert.assertEquals(path, decodedPath);

        encodedPath = URLUtil.encode(path, "GBK");
        decodedPath = URLUtil.decode(encodedPath, "GBK");
        Assert.assertEquals(path, decodedPath);
    }

    @Test
    public void testResolvePath() {
        String path = null;
        String expectedPath = null;
        String resolvedPath = null;

        resolvedPath = URLUtil.resolvePath(path);
        Assert.assertEquals(expectedPath, resolvedPath);

        path = "";
        expectedPath = "";
        resolvedPath = URLUtil.resolvePath(path);
        Assert.assertEquals(expectedPath, resolvedPath);

        path = "/abc/";
        expectedPath = "/abc/";
        resolvedPath = URLUtil.resolvePath(path);
        Assert.assertEquals(expectedPath, resolvedPath);

        path = "//abc///";
        expectedPath = "/abc/";
        resolvedPath = URLUtil.resolvePath(path);
        Assert.assertEquals(expectedPath, resolvedPath);


        path = "//\\abc///";
        expectedPath = "/abc/";
        resolvedPath = URLUtil.resolvePath(path);
        Assert.assertEquals(expectedPath, resolvedPath);
    }

    @Test
    public void testResolveRelativePath() {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        URL resourceURL = ClassLoaderUtil.getClassResource(classLoader, String.class);
        String expectedPath = "java/lang/String.class";
        String relativePath = URLUtil.resolveRelativePath(resourceURL);
        Assert.assertEquals(expectedPath, relativePath);
    }

    @Test
    public void testResolveParametersMap() {
        String url = "https://www.google.com.hk/search?q=java&oq=java&sourceid=chrome&es_sm=122&ie=UTF-8";
        Map<String, List<String>> parametersMap = URLUtil.resolveParametersMap(url);
        Map<String, List<String>> expectedParametersMap = Maps.newLinkedHashMap();
        expectedParametersMap.put("q", Arrays.asList("java"));
        expectedParametersMap.put("oq", Arrays.asList("java"));
        expectedParametersMap.put("sourceid", Arrays.asList("chrome"));
        expectedParametersMap.put("es_sm", Arrays.asList("122"));
        expectedParametersMap.put("ie", Arrays.asList("UTF-8"));

        Assert.assertEquals(expectedParametersMap, parametersMap);

        url = "https://www.google.com.hk/search";
        parametersMap = URLUtil.resolveParametersMap(url);
        expectedParametersMap = Collections.emptyMap();
        Assert.assertEquals(expectedParametersMap, parametersMap);

        url = "https://www.google.com.hk/search?";
        parametersMap = URLUtil.resolveParametersMap(url);
        expectedParametersMap = Collections.emptyMap();
        Assert.assertEquals(expectedParametersMap, parametersMap);
    }
}