<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
    $(function() {
       if (repParams.onClose != undefined) {
           repParams.onClose('<s:property value="%{requester}" />');
        }
    });
</script>