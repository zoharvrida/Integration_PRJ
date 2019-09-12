<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<script type="text/javascript">
    $(function() {
        if (destParams.onClose != undefined) {
            destParams.onClose('<s:property value="%{equiAmt}" />',
                               '<s:property value="%{destRate}" />',
                               '<s:property value="%{amtdestination}" />');
        }
    });
</script>