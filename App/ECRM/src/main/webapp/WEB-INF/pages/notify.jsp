<%--
  Created by IntelliJ IDEA.
  User: QuangTV
  Date: 6/22/2015
  Time: 9:01 AM
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%
  String pushStatus = "";
  Object pushStatusObj = request.getAttribute("pushStatus");

  if (pushStatusObj != null) {
    pushStatus = pushStatusObj.toString();
  }
%>
<head>
  <title>Google Cloud Messaging (GCM) Server in PHP</title>
</head>
<body>

<h1>Google Cloud Messaging (GCM) Server in Java</h1>

<form action="GCMNotification" method="post">

  <div>
			<textarea rows="2" name="message" cols="23"
                      placeholder="Message to transmit via GCM"></textarea>
  </div>
  <div>
    <input type="submit" value="Send Push Notification via GCM" />
  </div>
</form>
<p>
<h3>
  <%=pushStatus%>
</h3>
</p>
</body>
</html>
