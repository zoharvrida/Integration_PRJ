<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>

<meta http-equiv="refresh" content="5;url=<s:url includeParams="all" />"/>

<s:set var="requestMethod" scope="request">${pageContext.request.method}</s:set>

<s:if test="%{#request.requestMethod.equals('POST')}">
    <s:actionmessage id="actionMessage" />
    <s:actionerror name="actionError"/>
    <s:form id="waitfrm" name="waitfrm" action="25501" method="post" validate="true" theme="css_xhtml">

        <fieldset id="OpenGift" class="ui-widget-content ui-corner-all" style="border-radius: 1px">
            <legend class="ui-widget-header ui-corner-all"><s:label key="label.amortize.fieldset.legend.gifOpening" cssStyle="display:none"/></legend>
            <table>
                <tbody>

                    <tr>
                        <td colspan="1">
                            <s:label key="label.amortize.Account.no" cssStyle="display:none"/>
                        </td>
                    </tr>
                    <tr>
                        <td	class="tdLabel">
                            <s:label key="label.amortize.inprogress" cssStyle="display:none"/>
                            <sj:progressbar key="label.amortize.inprogress" name="progressing" />
                        </td>
                    </tr>
                </tbody>
            </table>
        </fieldset>


        <s:token />
    </s:form>
    <br>

    <script type="text/javascript">

        $(function() {
            $("#tabbedPanel").hide();
            $('#tabbedPanel ul').attr('style', 'display:none');
            if ($("#actionError").length == 0 && $("#actionMessage").length == 0) {
                $("#rbBS").buttonset("destroy");
                $("#tempTitle").click();
                $("#tempButtons").click();
            }
            else {
                $("#rbBS").data("rdb").callCurrent();
                if ($("#actionError").length == 0) {
                    $("#rbBS").data("rdb").clear(false);
                }
            }
        });
	
    </script>
</s:if>