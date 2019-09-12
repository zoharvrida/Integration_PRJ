<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<script type="text/javascript">
    $(function() {
        if (calcParams.onClose != undefined) {
            calcParams.onClose('<s:property value="%{amtTxn}" />',
                               '<s:property value="%{codCcy1}" />',
                               '<s:property value="%{usdEquivalent}"/>');
        }
    });
</script>