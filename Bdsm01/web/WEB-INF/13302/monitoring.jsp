<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
    $(function() {
       if (monParams.onClose != undefined) {
           monParams.onClose('<s:property value="%{monitoring}" />');
        }
    });
</script>