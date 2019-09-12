<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
    $(function() {
        if (tmpParams.onClose != undefined) {
            tmpParams.onClose('<s:property value="%{namCustFull}" />');
        }
    });
</script>