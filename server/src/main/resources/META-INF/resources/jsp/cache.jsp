<!DOCTYPE html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">
<body>
	<div class="container">
		<h1>Cached Files</h1>
		<ul>
			<c:forEach items="${cache}" var="file">
				<li>${file}</li>
			</c:forEach>
		</ul>
		<%-- <h1>Scheduled Updaters</h1>
		<ul>
			<c:forEach items="${scheduled}" var="future">
				<li>${future.}</li>
			</c:forEach>
		</ul> --%>
	</div>
</body>
</html>