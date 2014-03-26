<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
   <%@ page import="java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Note from the Past</title>
</head>
<body>
<%
String fromEmail = "";
String toEmail = "";
String subject = "";
String message = "";
String dateError="";
String emailToError="";
String emailFromError="";
Date dNow = new Date();
Calendar cal = new GregorianCalendar();
int year = cal.get(Calendar.YEAR);
int month = (cal.get(Calendar.MONTH) + 1);
int day = cal.get(Calendar.DAY_OF_MONTH);

String[] numArray = {"01","02","03","04","05","06","07","08","09"};
String[] monthArray = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};

try{
fromEmail = request.getAttribute("emailFrom").toString();
toEmail = request.getAttribute("emailTo").toString();
subject = request.getAttribute("subject").toString();
message = request.getAttribute("message").toString();
dateError = request.getAttribute("dateError").toString();
emailToError = request.getAttribute("emailToError").toString();
emailFromError = request.getAttribute("emailFromError").toString();
day = Integer.parseInt(request.getAttribute("day").toString());
month = Integer.parseInt(request.getAttribute("month").toString());
year = Integer.parseInt(request.getAttribute("year").toString());

} catch (Exception e)
{
}

%>
<form action="StoreEmail" method="get" id="emailform">
	<table>
	<tr valign="top">
	   	<td>
	   	<font color=
   		<% if (emailFromError.equals("true")){
   		%>'red'<%
   		} else {
   		%>'black'<%
   		}%>>Email From:</font></td>  
	   	<td><input type="text" name="emailFrom" size=50 value="<%=fromEmail%>"></td>
	   	<% if (emailFromError.equals("true")){
   		%><td><font color='red'> Please enter a valid email address</font></td><%
   		}%> 
   	</tr>
   	<tr valign="top">
	   	<td>
	   	<font color=
   		<% if (emailToError.equals("true")){
   		%>'red'<%
   		} else {
   		%>'black'<%
   		}%>>Email To:</font></td> 
	   	<td><input type="text" name="emailTo" size=50 value="<%=toEmail%>"></td>
	   	<% if (emailToError.equals("true")){
   		%><td><font color='red'> Please enter a valid email address</font></td><%
   		}%>
   	</tr>
   	<tr valign="top">
   		<td>Subject:</td> 
   		<td><input type="text" name="emailSubject" align="right" size=50 value="<%=subject%>"> </td>
   	</tr>
   	<tr valign="top">
   		<td>Message:</td>
   		<td><textarea name="emailMessage" form="emailform" rows=5 cols=36><%=message%></textarea> </td>
   	</tr>
   	<tr valign="top">
   		<td>
   		<font color=
   		<% if (dateError.equals("true")){
   		%>'red'<%
   		} else {
   		%>'black'<%
   		}%>>Date to Deliver:</font></td>
   		<td>
			<%
			//Day List
			out.print("<select name=\"Day\"> \n");
			for (int i=1; i<32; i++)
			{
				if (i<10){
					if (i == day){
						out.print("<option value=\"" + numArray[i-1] + "\" selected>" + numArray[i-1] + "</option> \n");
					} else {
					out.print("<option value=\"" + numArray[i-1] + "\">" + numArray[i-1] + "</option> \n");
					}
				}else{
					if (i == day){
						out.print("<option value=\"" + i + "\" selected>" + i + "</option> \n");
					} else {
					out.print("<option value=\"" + i + "\">" + i + "</option> \n");
					}
				}
				
			}
			
			out.print("</select>");
			
			
			//Month List
			out.print("<select name=\"Month\"> \n");
			for (int i=1; i<13; i++)
			{
				if (i<10){
					if (i == month){
						out.print("<option value=\"" + numArray[i-1] + "\" selected>" + monthArray[i-1] + "</option> \n");
					} else {
					out.print("<option value=\"" + numArray[i-1] + "\">" + monthArray[i-1] + "</option> \n");
					}
				} else {
					if (i == month){
						out.print("<option value=\"" + i + "\" selected>" + monthArray[i-1] + "</option> \n");
					} else {
					out.print("<option value=\"" + i + "\">" + monthArray[i-1] + "</option> \n");
					}
				}
			}
			
			out.print("</select>");
			
			
			//Year List
			out.print("<select name=\"Year\"> \n");
			for (int i=2014; i<2025; i++)
			{
				if (i == year){
					out.print("<option value=\"" + i + "\" selected>" + i + "</option> \n");
				} else {
				out.print("<option value=\"" + i + "\">" + i + "</option> \n");
				}
			}
			
			out.print("</select>");
			
			%>
			</td>
			
   			<td><% if (dateError.equals("true")){
   		%><font color='red'> Please enter a valid date</font><%
   		}%>
   		</td>
   	</tr>
	</table>
	
	<input type="submit" name="submit" value="Send a Note from the Past!">
</form>

</body>
</html>