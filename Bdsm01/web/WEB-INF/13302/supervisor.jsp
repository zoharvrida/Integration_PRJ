<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
    $(function() {
       if (supParams.onClose != undefined) {
           supParams.onClose('<s:property value="%{supervisor}" />');
        }
    });
</script>