<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
    $(function() {
        if (tempParams.onClose != undefined) {
            model = <s:property value="%{contentData}" escapeHtml="false" />;
            tempParams.onClose(model);
        }
    });
</script>