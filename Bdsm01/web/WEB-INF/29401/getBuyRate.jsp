<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<script type="text/javascript">
    $(function() {
        if (midParams.onClose != undefined) {
            midParams.onClose('<s:property value="%{codCcy1}" />',
                              '<s:property value="%{buyRate}" />');
        }
    });
</script>