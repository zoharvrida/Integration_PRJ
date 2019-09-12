<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
    $(function() {
       if (passParams.onClose != undefined) {
           //alert("GetONE");
           passParams.onClose('<s:property value="%{password}" />');
        }
    });
</script>