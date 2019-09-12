<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
    $(function() {
       if (trxParams.onClose != undefined) {
           //alert("GetONE");
           trxParams.onClose('<s:property value="%{txnId}" />');
        }
    });
</script>