<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sj" uri="/struts-jquery-tags" %>


<s:set var="requestMethod" scope="request">${pageContext.request.method}</s:set>

<s:if test="%{#request.requestMethod.equals('POST')}">
    <script type="text/javascript" src="_js/validation_theme_css_xhtml.js"></script>
    <script type="text/javascript" src="_js/pdf/fpdf.bundled.js"></script>

    <s:url var="ajaxUpdateTitle" action="32102_title_" />
    <s:url var="ajaxUpdateButton" action="32102_buttons" />
    <sj:a id="tempTitle" href="%{ajaxUpdateTitle}" targets="ph-title" cssClass="ui-helper-hidden" />
    <sj:a id="tempButtons" href="%{ajaxUpdateButton}" targets="ph-buttons" cssClass="ui-helper-hidden" />

    <s:actionmessage id="actionMessage" />
    <s:actionerror name="actionError" />
    <s:form id="frmMain" >
        <table>
            <tbody>
                <tr>
                    <td>
                        <s:label id="lblntb" key="label.ntb" />
                    </td>
                    <td>
                        <s:textfield 
                            name="ntb"
                            size="40"
                            cssClass="cls-alphabet-spc ui-widget ui-widget-content"
                            />
                    </td>
                    <td>
                        <s:label id="lblnptn" key="label.ntpn" />
                    </td>
                    <td>
                        <s:textfield 
                            name="ntpn"
                            size="40"
                            cssClass="cls-alphabet-spc ui-widget ui-widget-content"
                            />
                    </td>
                </tr>
                <tr>
                    <td>
                        <s:label id="lbltanggalBuku" key="label.tanggalBuku" />
                    </td>
                    <td>
                        <s:textfield 
                            name="tanggalBuku"
                            size="40"
                            cssClass="cls-alphabet-spc ui-widget ui-widget-content"
                            />
                    </td>
                    <td>
                        <s:label id="lbltanggalTransaksi" key="label.tanggalTransaksi" />
                    </td>
                    <td>
                        <s:textfield 
                            name="tanggalTransaksi"
                            size="40"
                            cssClass="cls-alphabet-spc ui-widget ui-widget-content"
                            />
                    </td>
                </tr>
                <tr>
                    <td>
                        <s:label id="lblresponseCode" key="label.responseCode" />
                    </td>
                    <td>
                        <s:textfield 
                            name="responseCode"
                            size="40"
                            cssClass="cls-alphabet-spc ui-widget ui-widget-content"
                            />
                    </td>
                    <td>
                        <s:label id="lblstan" key="label.stan" />
                    </td>
                    <td>
                        <s:textfield 
                            name="stan"
                            size="40"
                            cssClass="cls-alphabet-spc ui-widget ui-widget-content"
                            />
                    </td>
                </tr>
                <tr>
                    <td>
                        <s:label id="lblresponseDescription" key="label.responseDescription" />
                    </td>
                    <td>
                        <s:textfield 
                            name="responseDescription"
                            size="40"
                            cssClass="cls-alphabet-spc ui-widget ui-widget-content"
                            />
                    </td>
                    <td>
                        <s:label id="lblnoRefTransaksi" key="label.noRefTransaksi" />
                    </td>
                    <td>
                        <s:textfield 
                            name="noRefTransaksi"
                            size="40"
                            cssClass="cls-alphabet-spc ui-widget ui-widget-content"
                            />
                    </td>
                </tr>
                <tr></tr>
                <tr>
                    <td></td>
                    <td>
                        <sj:a id="btnConfirm" button="true" key="button.button.cetak.bpn">Cetak BPN</sj:a>
                        <sj:a id="btnPaymentCancel" button="true" key="button.kembali">Kembali</sj:a>
                        </td>
                    </tr>
                </tbody>
            </table>
        <s:token />
    </s:form>

</s:if>