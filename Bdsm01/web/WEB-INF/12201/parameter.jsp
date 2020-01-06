<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
    $(function() {
       if (labelParams.onClose != undefined) {
           labelParams.onClose('<s:property value="%{parameter}" />',
                               '<s:property value="%{format}" />',
                               '<s:property value="%{mandatory}" />',
                               '<s:property value="%{maxLength}" />',
                               '<s:property value="%{customQuery}" />',
                               '<s:property value="%{regexp}" escape="true" escapeJavaScript="true" />');
        }
    });
</script>