<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Note from the Past</title>
</head>
<body>
<form action="StoreEmail" method="get" id="emailform">
	<table>
	<tr valign="top">
	   	<td>Email From:</td> 
	   	<td><input type="text" name="emailFrom" size=50></td> 
   	</tr>
   	<tr valign="top">
	   	<td>Email To:</td> 
	   	<td><input type="text" name="emailTo" size=50></td> 
   	</tr>
   	<tr valign="top">
   		<td>Subject:</td> 
   		<td><input type="text" name="emailSubject" align="right" size=50> </td>
   	</tr>
   	<tr valign="top">
   		<td>Message:</td>
   		<td><textarea name="emailMessage" form="emailform" rows=5 cols=36>Your Message Here</textarea> </td>
   	</tr>
   	<tr valign="top">
   		<td>Date to Deliver:</td>
   		<td><select name="Day">
			<option value="01">1</option>
			<option value="02">2</option>
			<option value="03">3</option>
			<option value="04">4</option>
			<option value="05">5</option>
			<option value="06">6</option>
			<option value="07">7</option>
			<option value="08">8</option>
			<option value="09">9</option>
			<option value="10">10</option>
			<option value="11">11</option>
			<option value="12">12</option>
			<option value="13">13</option>
			<option value="14">14</option>
			<option value="15">15</option>
			<option value="16">16</option>
			<option value="17">17</option>
			<option value="18">18</option>
			<option value="19">19</option>
			<option value="20">20</option>
			<option value="21">21</option>
			<option value="22">22</option>
			<option value="23">23</option>
			<option value="24">24</option>
			<option value="25">25</option>
			<option value="26">26</option>
			<option value="27">27</option>
			<option value="28">28</option>
			<option value="29">29</option>
			<option value="30">30</option>
			<option value="31">31</option>
			</select>
			<select name="Month">
			<option value="01">Jan</option>
			<option value="02">Feb</option>
			<option value="03">Mar</option>
			<option value="04">Apr</option>
			<option value="05">May</option>
			<option value="06">Jun</option>
			<option value="07">Jul</option>
			<option value="08">Aug</option>
			<option value="09">Sep</option>
			<option value="10">Oct</option>
			<option value="11">Nov</option>
			<option value="12">Dec</option>
			</select>
			<select name="Year">
			<option value="2014">2014</option>
			<option value="2015">2015</option>
			<option value="2016">2016</option>
			<option value="2017">2017</option>
			<option value="2018">2018</option>
			<option value="2019">2019</option>
			<option value="2020">2020</option>
			<option value="2021">2021</option>
			<option value="2022">2022</option>
			<option value="2023">2023</option>
			<option value="2024">2024</option>
			</select></td>
   	
   	</tr>
	</table>
	
	<input type="submit" name="submit" value="Send a Note from the Past!">
</form>

</body>
</html>