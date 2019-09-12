<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
    $(function() {
        if (tempParams.onClose != undefined) {
            so = <s:property value="%{so}" escapeHtml="false" />;
            tempParams.onClose(so);
        }
    });
</script>