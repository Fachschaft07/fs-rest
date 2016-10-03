<!DOCTYPE html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">
	<body>
		<div class="container">
			<h1>Endpoints</h1>
			<ul>
			<c:forEach items="${urls}" var="entry">
				<c:if test="${ entry ne '' and entry ne 'error' and entry ne 'cache' and entry ne 'v2/api-docs' and entry ne 'configuration/security' and entry ne 'configuration/ui' and entry ne 'swagger-resources' }">
					<li>
						<a href="${entry}">${entry}</a>
					</li>
				</c:if>
			</c:forEach>
			</ul>
		</div>
	</body>
</html>