<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<script type="text/javascript">
    $(function() {
        if (calcParams.onClose != undefined) {
            calcParams.onClose('<s:property value="%{amtTxn}" />',
                               '<s:property value="%{reason}" />',
                               '<s:property value="%{directLink}" escapeHtml="false" escapeJavaScript="true"/>',
                               '<s:property value="%{scrnData}" escapeHtml="false" escapeJavaScript="true"/>');
        }
    });
</script>