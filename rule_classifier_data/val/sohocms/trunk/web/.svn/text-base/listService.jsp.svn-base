
<ol>
<%
java.util.ArrayList list = (java.util.ArrayList)session.getAttribute("list");
String command = request.getParameter("command");
String uuid = request.getParameter("uuid");
String service = request.getParameter("service");

if (list == null) {
    list = new java.util.ArrayList();
    session.setAttribute("list", list);
}

if ("add".equals(command)) {
    String item = request.getParameter("entry");
    list.add(item);
}

if ("remove".equals(command)) {
    String indexString = request.getParameter("index");
    if (indexString != null) {
        int index = Integer.valueOf(indexString);
        list.remove(index);
    }
}
java.util.Iterator it = list.iterator();
int counter=0;
while(it.hasNext()) {
  out.println("<li><div class=\"plain\" onmouseover=\"this.className ='over';\" onmouseout=\"this.className ='plain';\" onclick=\"jmaki.attributes.get('" + uuid + "').removeItem('" + counter++ + "')\">" + it.next() + "</div></li>");
}
%>
</ol>
