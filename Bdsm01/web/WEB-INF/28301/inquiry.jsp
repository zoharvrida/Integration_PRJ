<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
    $(function() {
        if (tempParams.onClose != undefined) {
            tempParams.onClose('<s:property value="%{namCustFull}" />', 
                               '<s:property value="%{cdBranch}" />',
                               '<s:property value="%{slCreatorUser}" />');
        }
    });
</script>