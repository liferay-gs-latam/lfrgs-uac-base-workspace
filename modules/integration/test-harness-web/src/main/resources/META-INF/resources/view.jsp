<%@ include file="/init.jsp" %>

<%
String resolvedModuleName = GetterUtil.getString(request.getAttribute("resolvedModuleName"));
%>

<portlet:actionURL name="<%= TestHarnessMVCActionCommand.MVC_COMMAND_NAME %>" var="invokeURL" />

<div class="api-container">
	<liferay-frontend:screen-navigation
		key="<%= TestHarnessConstants.SCREEN_NAVIGATION_KEY %>"
		portletURL="<%= currentURLObj %>"
	/>
</div>

<aui:script require="<%= resolvedModuleName %>">
	var TestHarnessObj = TestHarness.default;
	var testHarness = new TestHarnessObj('.api-container');
</aui:script>