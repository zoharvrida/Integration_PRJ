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
                            name="billCode"
                            size="40"
                            cssClass="cls-alphabet-spc ui-widget ui-widget-content"
                            />
                    </td>
                    <td>
                        <s:label id="lblnptn" key="label.etax.ntpn" />
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
                        <s:label id="lbltanggalBuku" key="label.etax.tanggalBuku" />
                    </td>
                    <td>
                        <s:textfield 
                            name="tanggalBuku"
                            size="40"
                            cssClass="cls-alphabet-spc ui-widget ui-widget-content"
                            />
                    </td>
                    <td>
                        <s:label id="lbltanggalTransaksi" key="label.etax.tanggalTransaksi" />
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
                        <s:label id="lblresponseCode" key="label.etax.responseCode" />
                    </td>
                    <td>
                        <s:textfield 
                            name="responseCode"
                            size="40"
                            cssClass="cls-alphabet-spc ui-widget ui-widget-content"
                            />
                    </td>
                    <td>
                        <s:label id="lblstan" key="label.etax.stan" />
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
                        <s:label id="lblresponseDescription" key="label.etax.responseDescription" />
                    </td>
                    <td>
                        <s:textfield 
                            name="responseDescription"
                            size="40"
                            cssClass="cls-alphabet-spc ui-widget ui-widget-content"
                            />
                    </td>
                    <td>
                        <s:label id="lblnoRefTransaksi" key="label.etax.reffNo" />
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
       <s:token name="paymentToken"/>
    </s:form>