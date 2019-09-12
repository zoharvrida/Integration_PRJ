<%@page contentType="text/html" pageEncoding="UTF-8"
%><%@ taglib prefix="s" uri="/struts-tags"
%><%@ taglib prefix="sj" uri="/struts-jquery-tags"
%><script type="text/javascript">
    $(function() {
        alert("<s:property value="errorMessage" />");
        $("#ph-title").html("&nbsp;");
        $("#ph-buttons").html("&nbsp;");
    });
</script>