<%@ page contentType="text/html;charset=UTF-8" %>

<%@ page import="java.io.PrintWriter" %>

<%@ page import="java.sql.Timestamp" %>

<%@ page import="java.util.Enumeration" %>
<%@ page import="java.util.Locale" %>

<%@ page import="javax.servlet.http.Cookie" %>

<%@ include file="/header.jsp" %>

<%
    HttpServletRequest expRequest = ( HttpServletRequest )request.getAttribute( "cause.request" );
    Exception e = ( Exception )request.getAttribute( "cause.exception" );

    String log = "\r\n" + "Request Received at " + ( new Timestamp( System.currentTimeMillis() ) );
    log += "\r\n" + "    characterEncoding=" + expRequest.getCharacterEncoding();
    log += "\r\n" + "    contentLength=" + expRequest.getContentLength();
    log += "\r\n" + "    contentType=" + expRequest.getContentType();
    log += "\r\n" + "    locale=" + expRequest.getLocale();

    log += "\r\n" + "    locales=";
    Enumeration locales = expRequest.getLocales();
    boolean first = true;
    while ( locales.hasMoreElements() ) {

        Locale locale = ( Locale ) locales.nextElement();

        if ( first )
            first = false;
        else
            log += ", ";

        log += locale.toString();

    } // end while

    log += "\r\n";

    Enumeration names = expRequest.getParameterNames();
    while ( names.hasMoreElements() ) {

        String name = ( String )names.nextElement();
        log += "    parameter=" + name + "=";

        String values[] = expRequest.getParameterValues(name);

        for ( int i = 0; i < values.length; i++ ) {

            if (i > 0)
                log += ", ";

            log += values[ i ];

        } // end for i

        log += "\r\n";

    } // end while

    log += "    protocol=" + expRequest.getProtocol();
    log += "\r\n" + "    remoteAddr=" + expRequest.getRemoteAddr();
    log += "\r\n" + "    remoteHost=" + expRequest.getRemoteHost();
    log += "\r\n" + "    scheme=" + expRequest.getScheme();
    log += "\r\n" + "    serverName=" + expRequest.getServerName();
    log += "\r\n" + "    serverPort=" + expRequest.getServerPort();
    log += "\r\n" + "    isSecure=" + expRequest.isSecure();

    if ( expRequest instanceof HttpServletRequest ) {

        log += "\r\n" + "---------------------------------------------";
        HttpServletRequest hexpRequest = ( HttpServletRequest ) expRequest;

        log += "\r\n" + "    contextPath=" + hexpRequest.getContextPath();

        Cookie cookies[] = hexpRequest.getCookies();
        if (cookies == null)
            cookies = new Cookie[0];

        for ( int i = 0; i < cookies.length; i++ )
            log += "\r\n" + "    cookie=" + cookies[ i ].getName() + "=" + cookies[ i ].getValue();

        names = hexpRequest.getHeaderNames();
        while ( names.hasMoreElements() ) {

            String name = ( String ) names.nextElement();
            String value = hexpRequest.getHeader(name);
            log += "\r\n" + "    header=" + name + "=" + value;

        } // end while

        log += "\r\n" + "    method=" + hexpRequest.getMethod();
        log += "\r\n" + "    pathInfo=" + hexpRequest.getPathInfo();
        log += "\r\n" + "    queryString=" + hexpRequest.getQueryString();
        log += "\r\n" + "    remoteUser=" + hexpRequest.getRemoteUser();
        log += "\r\n" + "    expRequestedSessionId=" + hexpRequest.getRequestedSessionId();
        log += "\r\n" + "    expRequestURI=" + hexpRequest.getRequestURI();
        log += "\r\n" + "    servletPath=" + hexpRequest.getServletPath();

    } // end if

    log += "\r\n" + "=============================================";
%>

<table border="0" cellspacing="0" cellpadding="0">

    <tr>
        <td class="td_default">
            exception message
        </td>
    </tr>
    <tr>
        <td class="td_red">
            <%=e.getMessage()%>
        </td>
    </tr>

    <tr><td height="10"></td></tr>

    <tr>
        <td class="td_default">
            exception stack trace
        </td>
    </tr>
    <tr>
        <td class="td_gray">
            <pre><% e.printStackTrace( new PrintWriter( out ) ); %></pre>
        </td>
    </tr>

    <tr><td height="10"></td></tr>

    <tr>
        <td class="td_default">
            request information
        </td>
    </tr>
    <tr>
        <td class="td_gray">
            <pre><%=log%></pre>
        </td>
    </tr>

</table>