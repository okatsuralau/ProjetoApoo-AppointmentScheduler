<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:forEach var="registro" items="${availabilities}">
	<tr>
		<td>${registro.doctor.individual.first_name}</td>
		<td>${registro.expertise.title}</td>
		<td>${registro.office.title}</td>
		<td>${registro.availability_date}</td>
		<td>${registro.availability_time}</td>
		<td>${registro.available_amount}</td>
		<td>${registro.scheduled_amount}</td>
		<td>${registro.available_amount - registro.scheduled_amount}</td>
	</tr>
</c:forEach>