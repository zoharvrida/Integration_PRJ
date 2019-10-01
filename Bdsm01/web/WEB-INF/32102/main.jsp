<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sj" uri="/struts-jquery-tags" %>

    <s:set var="requestMethod" scope="request">${pageContext.request.method}</s:set>

    <s:form id="frmMain" name="frmMain"  theme="css_xhtml">
        <fieldset id="fsDataPayment" class="ui-widget-content ui-corner-all">
			<legend class="ui-widget-header ui-corner-all"><s:text name="label.etax.fieldset.legend.mpn.response" /></legend>
        <table>
            <tbody>
                <tr>
                    <td>
                        <s:label id="lblntb" key="label.etax.ntb" />
                    </td>
                    <td>
                        <s:textfield 
                            name="epv.refNtb"
                            size="40"
                            cssClass="cls-alphabet-spc ui-widget ui-widget-content"
                            disabled="true"
                            />
                    </td>
                    <td>
                        <s:label id="lblnptn" key="label.etax.ntpn" />
                    </td>
                    <td>
                        <s:textfield 
                            name="epv.refNtpn"
                            size="40"
                            cssClass="cls-alphabet-spc ui-widget ui-widget-content"
                            disabled="true"
                            />
                    </td>
                </tr>
                <tr>
                    <td>
                        <s:label id="lbltanggalBuku" key="label.etax.tanggalBuku" />
                    </td>
                    <td>
                        <s:textfield 
                            name="epv.dtmPost"
                            size="40"
                            cssClass="cls-alphabet-spc ui-widget ui-widget-content"
                            disabled="true"
                            />
                    </td>
                    <td>
                        <s:label id="lbltanggalTransaksi" key="label.etax.tanggalTransaksi" />
                    </td>
                    <s:hidden name ="epv.limitVal.errCode" />
                    <s:hidden name ="epv.limitVal.errDesc" />
                    <td>
                        <s:textfield 
                            name="epv.dtmTrx"
                            size="40"
                            cssClass="cls-alphabet-spc ui-widget ui-widget-content"
                            disabled="true"
                            />
                    </td>
                </tr>
                <tr>
                    <td>
                        <s:label id="lblresponseCode" key="label.etax.responseCode" />
                    </td>
                    <td>
                        <s:textfield 
                            name="epv.errCode"
                            size="40"
                            cssClass="cls-alphabet-spc ui-widget ui-widget-content"
                            disabled="true"
                            />
                    </td>
                    <td>
                        <s:label id="lblstan" key="label.etax.stan" />
                    </td>
                    <td>
                        <s:textfield 
                            name="epv.codStanId"
                            size="40"
                            cssClass="cls-alphabet-spc ui-widget ui-widget-content"
                            disabled="true"
                            />
                    </td>
                </tr>
                <tr>
                    <td>
                        <s:label id="lblresponseDescription" key="label.etax.responseDescription" />
                    </td>
                    <td>
                        <s:textfield 
                            name="epv.errDesc"
                            size="40"
                            cssClass="cls-alphabet-spc ui-widget ui-widget-content"
                            disabled="true"
                            />
                    </td>
                    <td>
                        <s:label id="lblnoRefTransaksi" key="label.etax.reffNo" />
                    </td>
                    <td>
                        <s:textfield 
                            name="epv.refUsrNo"
                            size="40"
                            cssClass="cls-alphabet-spc ui-widget ui-widget-content"
                            disabled="true"
                            />
                    </td>
                </tr>
                <tr></tr>
                <tr>
                    <td></td>
                    <td>
                        <sj:submit
                            id="btnCetak"
                            buttonIcon="ui-icon-gear"
                            button="true"
                            targets="ph-main"
                            key="button.generate.bpn"
                            disabled="true"
                            />
                        </td>
                    </tr>
                </tbody>
            </table>
        </fieldset>
                        <s:token name="payment"/>
    </s:form>