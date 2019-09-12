<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<script type="text/javascript">
    $(function() {
		if (tempParams.onClose != undefined) {
            tempParams.onClose('<s:property value="%{noAcct}" />',
                               '<s:property value="%{refTxn}" />',
                               '<s:property value="%{ccyTxn}" />',
                               '<s:property value="%{amtTxnStr}" />',
                               '<s:property value="%{ratFcyIdr}" />',
                               '<s:property value="%{amtTxnLcyStr}" />',
                               '<s:property value="%{ratUsdIdr}" />',
                               '<s:property value="%{amtTxnUsdStr}" />',
                               '<s:property value="%{txnDesc}" />',
                               '<s:property value="%{dtmTxn}" />',
                               '<s:property value="%{dtValue}" />',
                               '<s:property value="%{idTxn}" />',
                               '<s:property value="%{typMsg}" />',
                               '<s:property value="%{txnBranch}" />',
                               '<s:property value="%{typTrx}" />');
        }
    });
</script>