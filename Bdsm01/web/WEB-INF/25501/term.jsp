<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
    $(function() {
        if (labelParams.onClose != undefined) {
            labelParams.onClose('<s:property value="%{giftPrice}" />',
            '<s:property value="%{MinAmount}" />',
            '<s:property value="%{MaxAmount}" />',
            '<s:property value="%{idSequence}" />',
            '<s:property value="%{holdAmount}" />',
            '<s:property value="%{idProgram_details}" />',
            '<s:property value="%{Opendate}" />',
            '<s:property value="%{Term}" />',
        '<s:property value="%{Status}" />',
    '<s:property value="%{Canceldate}" />',
'<s:property value="%{giftGross}" />',
'<s:property value="%{taxAmount}" />');
        }
    });
</script>