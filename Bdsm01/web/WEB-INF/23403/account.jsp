<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
    $(function() {
       if (acctParams.onClose != undefined) {
           //alert("GetONE");
           acctParams.onClose('<s:property value="%{account}" />');
        }
    });
</script>