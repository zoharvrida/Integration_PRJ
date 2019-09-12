<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<script type="text/javascript">
    $(function() {
        if (rateParams.onClose != undefined) {
            rateParams.onClose('<s:property value="%{currencyCode}" />',
                               '<s:property value="%{sellRate}" />')
                               
        }
    });
</script>