<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<script type="text/javascript">
    $(function() {
        if (chParams.onClose != undefined) {
            chParams.onClose('<s:property value="%{noAcct}" />',
                             '<s:property value="%{acctTitle}" />',
                             '<s:property value="%{balAvail}" />',
                             '<s:property value="%{reason}" />',
                             '<s:property value="%{contentData}" escapeHtml="false" escapeJavaScript="true"/>',
                             '<s:property value="%{prodData}" escapeHtml="false" escapeJavaScript="true"/>');
        }
    });
</script>