<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sj" uri="/struts-jquery-tags" %>


<s:set var="requestMethod" scope="request">${pageContext.request.method}</s:set>

<s:if test="%{#request.requestMethod.equals('POST')}">
    <script type="text/javascript" src="_js/validation_theme_css_xhtml.js"></script>
    <script type="text/javascript" src="_js/pdf/fpdf.bundled.js"></script>

    <s:url var="ajaxUpdateTitle" action="32101_title_" />
    <s:url var="ajaxUpdateButton" action="32101_buttons" />
    <sj:a id="tempTitle" href="%{ajaxUpdateTitle}" targets="ph-title" cssClass="ui-helper-hidden" />
    <sj:a id="tempButtons" href="%{ajaxUpdateButton}" targets="ph-buttons" cssClass="ui-helper-hidden" />

    <s:actionmessage id="actionMessage" />
    <s:actionerror name="actionError" />

    <s:form id="formData" action="32101_inquiryBilling">
        <s:hidden name="billingId" />
        <s:hidden name="paymentType" />
        <s:token name="refTokens"/>
    </s:form>
    <sj:a id="tempformData" formIds="formData" targets="ph-temp" cssClass="ui-helper-hidden" onBeforeTopics="beforeSubmitInquiryBilling"></sj:a>
    <s:form id="formPosting" action="32102_doValidateLimit">
        <s:hidden name="billingId" />
        <s:hidden name="paymentType" />
        <s:hidden name="codAuthid" value="%{#session.idUser}" />
        <s:hidden name="idMaintainedSpv" />
        <s:token name="refTokens"/>
    </s:form>
    <sj:a id="tempformPosting" formIds="formPosting" targets="ph-temp" cssClass="ui-helper-hidden"></sj:a>

    <s:form id="frmDlgAuth" action="dlg">
        <s:hidden name="dialog" value="dlgAuth" />
        <s:hidden name="idMenu" value="%{#session.idMenu}" />
    </s:form>
    <sj:a id="aDlgAuth" formIds="frmDlgAuth" targets="ph-dlg" cssClass="ui-helper-hidden" />

    <s:form id="frmLoadNewToken" action="generateToken" cssClass="ui-helper-hidden">
        <s:hidden name="name" value="" />
        <s:token name="tokenForNewToken" />
    </s:form>
    <sj:a id="aLoadNewToken" formIds="frmLoadNewToken" targets="ph-temp" cssClass="ui-helper-hidden" />
    <s:form id="formMidRate" action="28202_getMidRate.action">
        <s:hidden name="ccyName" />
        <s:token name="ccyTokens"/>
    </s:form>
    <sj:a id="tempformMidRate" formIds="formMidRate" targets="ph-temp" cssClass="ui-helper-hidden"></sj:a>
    <s:form id="formValidateAcc" action="32101_inquiryAccount">
        <s:hidden name="noAccount" />
        <s:hidden name="typeAccount" />
        <s:token />
    </s:form>
    <sj:a id="tempValidateAcc" formIds="formValidateAcc" targets="ph-temp" cssClass="ui-helper-hidden" onSuccessTopics="postValAcc"></sj:a>
    <s:actionmessage id="actionMessage"/>
    <s:actionerror name="actionError" />
    <script type="text/javascript" src="_js/validation_theme_css_xhtml.js"></script>

    <s:form id="frmView" name="frmView">
        <s:hidden name="formId" />
        <s:hidden name="globalState" />
    </s:form>
    
    <s:form id="frmPayment" name="frmPayment" action="32101_inquiryBilling" theme="css_xhtml">
        <fieldset id="fsInquiry" class="ui-widget-content ui-corner-all" style="border: 0px; display: none;">
            <table>
                <tbody>
                    <tr>
                        <td colspan="4" id="errMsg" style="text-align:center"></td>
                    </tr>
                    <tr>
                        <td>
                            <s:label id="lblIDBilling" key="label.etax.idbilling" requiredLabel="true" />
                        </td>
                        <td>
                            <s:textfield 
                                name="billingId"
                                size="40"
                                maxlength="15"
                                cssClass="cls-numeric ui-widget ui-widget-content"
                                cssStyle=""
                                />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <s:label id="lblPaymentType" key="label.etax.payment.type" requiredLabel="true" />
                        </td>
                        <td>
                            <s:url id="getListPaymentCode" action="json/32101_listPaymentType"  />
                            <sj:select
                                id="frmPayment_paymentType"
                                name="paymentType"
                                href="%{getListPaymentCode}"
                                list="paymentTypes"
                                cssClass="ui-widget ui-widget-content"
                                emptyOption="true"
                                headerKey="0"
                                onSuccessTopics="reloadCurrencyList"
                                />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <sj:a id="btnOk" button="true" key="button.ok">OK</sj:a>
                            <sj:a id="btnCancel" button="true" key="button.clear">Clear</sj:a>
                            </td>
                        </tr>
                    </tbody>
                </table>

            <s:token />
            <hr class="ui-widget-content" />
        </fieldset>

        <div id="divETaxAkhir"></div>
        <div id="divETaxPelunasanMessage"></div>
    </s:form>
    <s:form id="frmReinquiry" name="frmReinquiry" action="32101_reInquiryBilling" theme="css_xhtml">
        <fieldset id="fsReinquiry" class="ui-widget-content ui-corner-all" style="border: 0px; display: none;">
            <table>
                <tbody>
                    <tr>
                        <td>
                            <s:label id="lblIDBillingReinq" key="label.etax.idbilling" requiredLabel="true" />
                        </td>
                        <td>
                            <s:textfield 
                                name="billingId"
                                size="40"
                                maxlength="15"
                                cssClass="cls-numeric ui-widget ui-widget-content"
                                cssStyle=""
                                />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <sj:a id="btnReinqOk" button="true" key="button.ok">OK</sj:a>
                            <sj:a id="btnReinqClear" button="true" key="button.clear">Clear</sj:a>
                            </td>
                        </tr>
                </tbody>
            </table>

            <s:token />
            <hr class="ui-widget-content" />
        </fieldset>
    </s:form>

    <s:form id="frmMain" name="frmMain" action="32101_inquiryBilling" theme="css_xhtml" style="">
        <fieldset id="fsDataETax" class="ui-widget-content ui-corner-all" style="display: none;">
            <legend class="ui-widget-header ui-corner-all"><s:text name="label.etax.fieldset.legend.data.billing" /></legend>
            <fieldset id="fsBillingInfo" class="ui-widget-content ui-corner-all">
                <legend class="ui-widget-header ui-corner-all"><s:text name="label.etax.fieldset.legend.billing.info" /></legend>
                <table id="tableBillingInfo">
                    <tbody>
                        <tr>
                            <td>
                                <s:label id="lblIDBilling" key="label.etax.idbilling" />
                            </td>
                            <td>
                                <s:textfield 
                                    name="etax.billingInfo.billingId"
                                    size="40"
                                    maxlength="15" 
                                    cssClass="cls-alphabet-spc ui-widget ui-widget-content"
                                    readonly="true"
                                    />
                            </td>

                            <td>
                                <s:label id="lblTrxTime" key="label.etax.trxTime" />
                            </td>
                            <td>
                                <s:textfield 
                                    name="etax.responseTimeString"
                                    size="40"
                                    maxlength="40" 
                                    cssClass="cls-alphabet-spc ui-widget ui-widget-content"
                                    readonly="true"
                                    />
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <s:label id="lblNPWP" key="label.etax.npwp" />
                            </td>
                            <td>
                                <s:textfield 
                                    name="etax.billingInfo.npwp"
                                    size="40"
                                    maxlength="40" 
                                    cssClass="cls-alphabet-spc ui-widget ui-widget-content"
                                    readonly="true"
                                    />
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <s:label id="lblWPName" key="label.etax.wpName" />
                            </td>
                            <td>
                                <s:textfield 
                                    name="etax.billingInfo.wpName"
                                    size="40"
                                    maxlength="40" 
                                    cssClass="cls-alphabet-spc ui-widget ui-widget-content"
                                    readonly="true"
                                    />
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <s:label id="lblIDWajibBayar" key="label.etax.idWajibBayar" />
                            </td>
                            <td>
                                <s:textfield 
                                    name="etax.billingInfo.idWajibBayar"
                                    size="40"
                                    maxlength="40" 
                                    cssClass="cls-alphabet-spc ui-widget ui-widget-content"
                                    readonly="true"
                                    />
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <s:label id="lblCurrency" key="label.etax.currency" />
                            </td>
                            <td colspan="3">
                                <s:textfield 
                                    name="etax.ccy"
                                    size="40"
                                    maxlength="40" 
                                    cssClass="cls-alphabet-spc ui-widget ui-widget-content"
                                    readonly="true"
                                    />
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <s:label id="lblNominal" key="label.etax.nominal" />
                            </td>
                            <td colspan="3">
                                <s:textfield 
                                    name="etax.amount"
                                    size="40"
                                    maxlength="40" 
                                    cssClass="cls-alphabet-spc ui-widget ui-widget-content"
                                    readonly="true"
                                    />
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <s:label id="lblWPAddress" key="label.etax.wpAddress" />
                            </td>
                            <td colspan="3">
                                <s:textarea 
                                    name="etax.billingInfo.wpAddress"
                                    cols="75"
                                    rows="2" 
                                    cssClass="cls-alphabet-spc ui-widget ui-widget-content"
                                    readonly="true"
                                    cssStyle="resize: none"
                                    />
                            </td>
                        </tr>
                    </tbody>
                </table>
            </fieldset>
            <fieldset id="fsBillingDetails" class="ui-widget-content ui-corner-all" disabled="disabled">
                <legend class="ui-widget-header ui-corner-all"><s:text name="label.etax.fieldset.legend.billing.details" /></legend>
                <fieldset id="fsBillingDetailsDJP" class="ui-widget-content ui-corner-all" style="display: none;">
                    <legend class="ui-widget-header ui-corner-all"><s:text name="label.etax.fieldset.legend.billing.details.djp" /></legend>
                    <table>
                        <tbody>
                            <tr>
                                <td>
                                    <s:label id="lblKodeMAP" key="label.etax.kodeMAP" />
                                </td>
                                <td>
                                    <s:textfield 
                                        name="etax.billingInfo.akun"
                                        size="40"
                                        cssClass="cls-alphabet-spc ui-widget ui-widget-content"
                                        readonly="true"
                                        />
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <s:label id="lblSKNumber" key="label.etax.skNumber" />
                                </td>
                                <td>
                                    <s:textfield 
                                        name="etax.billingInfo.nomorSK"
                                        size="40"
                                        cssClass="cls-alphabet-spc ui-widget ui-widget-content"
                                        readonly="true"
                                        />
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <s:label id="lblTaxPeriod" key="label.etax.taxPeriod" />
                                </td>
                                <td>
                                    <s:textfield 
                                        name="etax.billingInfo.masaPajak"
                                        size="40"
                                        cssClass="cls-alphabet-spc ui-widget ui-widget-content"
                                        readonly="true"
                                        />
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <s:label id="lblJenisSetoran" key="label.etax.jenisSetoran" />
                                </td>
                                <td>
                                    <s:textfield 
                                        name="etax.billingInfo.kodeJenisSetoran"
                                        size="40"
                                        cssClass="cls-alphabet-spc ui-widget ui-widget-content"
                                        readonly="true"
                                        />
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <s:label id="lblOPNumber" key="label.etax.opNumber" />
                                </td>
                                <td>
                                    <s:textfield 
                                        name="etax.billingInfo.nop"
                                        size="40"
                                        cssClass="cls-alphabet-spc ui-widget ui-widget-content"
                                        readonly="true"
                                        />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </fieldset>
                <fieldset id="fsBillingDetailsDJBC" class="ui-widget-content ui-corner-all" disabled="disabled" style="display: none;">
                    <legend class="ui-widget-header ui-corner-all"><s:text name="label.etax.fieldset.legend.billing.details.djbc" /></legend>
                    <table>
                        <tbody>
                            <tr>
                                <td>
                                    <s:label id="lblDocType" key="label.etax.docType" />
                                </td>
                                <td>
                                    <s:textfield 
                                        name="etax.billingInfo.jenisDokumen"
                                        size="40"
                                        cssClass="cls-alphabet-spc ui-widget ui-widget-content"
                                        readonly="true"
                                        />
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <s:label id="lblDocNumber" key="label.etax.docNumber" />
                                </td>
                                <td>
                                    <s:textfield 
                                        name="etax.billingInfo.nomorDokumen"
                                        size="40"
                                        cssClass="cls-alphabet-spc ui-widget ui-widget-content"
                                        readonly="true"
                                        />
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <s:label id="lblKPBCCode" key="label.etax.kpbcCode" />
                                </td>
                                <td>
                                    <s:textfield 
                                        name="etax.billingInfo.kodeKPBC"
                                        size="40"
                                        cssClass="cls-alphabet-spc ui-widget ui-widget-content"
                                        readonly="true"
                                        />
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <s:label id="lblDocDate" key="label.etax.docDate" />
                                </td>
                                <td>
                                    <s:textfield 
                                        name="etax.billingInfo.tanggalDokumen"
                                        size="40"
                                        cssClass="cls-alphabet-spc ui-widget ui-widget-content"
                                        readonly="true"
                                        />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </fieldset>
                <fieldset id="fsBillingDetailsDJA" class="ui-widget-content ui-corner-all" disabled="disabled" style="display: none;">
                    <legend class="ui-widget-header ui-corner-all"><s:text name="label.etax.fieldset.legend.billing.details.dja" /></legend>
                    <table>
                        <tbody>
                            <tr>
                                <td>
                                    <s:label id="lblKL" key="label.etax.kl" />
                                </td>
                                <td>
                                    <s:textfield 
                                        name="etax.billingInfo.kl"
                                        size="40"
                                        cssClass="cls-alphabet-spc ui-widget ui-widget-content"
                                        readonly="true"
                                        />
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <s:label id="lblUnitEselon1" key="label.etax.unitEselon1" />
                                </td>
                                <td>
                                    <s:textfield 
                                        name="etax.billingInfo.unitEselon1"
                                        size="40"
                                        cssClass="cls-alphabet-spc ui-widget ui-widget-content"
                                        readonly="true"
                                        />
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <s:label id="lblKodeSatker" key="label.etax.kodeSatker" />
                                </td>
                                <td>
                                    <s:textfield 
                                        name="etax.billingInfo.kodeSatker"
                                        size="40"
                                        cssClass="cls-alphabet-spc ui-widget ui-widget-content"
                                        readonly="true"
                                        />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </fieldset>
            </fieldset>
        </fieldset>
        <fieldset id="fsDebitCASA" class="ui-widget-content ui-corner-all" style="display: none;">
            <legend class="ui-widget-header ui-corner-all"><s:text name="label.etax.fieldset.legend.billing.debit" /></legend>
            <table>
                <tbody>
                    <tr>
                        <td>
                            <s:label id="lblDebitAccountNo" key="label.etax.debitAccountNo" requiredLabel="true" />
                        </td>
                        <td>
                            <s:textfield 
                                name="etax.debitAccountNo"
                                size="40"
                                maxlength="12" 
                                cssClass="cls-alphabet-spc ui-widget ui-widget-content"
                                readonly="false"
                                onblur="fetchDebitAccount('CASA');"
                                />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <s:label id="lblDebitAccountName" key="label.etax.debitAccountName" />
                        </td>
                        <td>
                            <s:textfield 
                                name="etax.debitAccountName"
                                size="40"
                                maxlength="12" 
                                cssClass="cls-alphabet-spc ui-widget ui-widget-content"
                                readonly="true"
                                />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <s:label id="lblTrxCurrency" key="label.etax.trxCurrency" requiredLabel="true" />
                        </td>
                        <td>
                            <s:url id="getListCurrency" action="json/32101_listCurrency"  />
                            <sj:select
                                id="frmPayment_trxCurrency"
                                name="etax.trxCurrency"
                                href="%{getListCurrency}"
                                list="currencies"
                                cssClass="ui-widget ui-widget-content"
                                emptyOption="false"
                                headerKey="0"
                                />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <s:label id="lblNominalLCE" key="label.etax.nominalLCE" />
                        </td>
                        <td>
                            <s:textfield 
                                name="etax.nominalLCE"
                                size="40"
                                maxlength="12" 
                                cssClass="cls-alphabet-spc ui-widget ui-widget-content"
                                readonly="true"
                                />
                        </td>
                    </tr>
                </tbody>
            </table>
        </fieldset>
        <fieldset id="fsDebitGL" class="ui-widget-content ui-corner-all" style="display: none;">
            <legend class="ui-widget-header ui-corner-all"><s:text name="label.etax.fieldset.legend.billing.debitGL" /></legend>
            <table>
                <tbody>
                    <tr>
                        <td>
                            <s:label id="lblDebitGLNo" key="label.etax.glNumber" requiredLabel="true" />
                        </td>
                        <td>
                            <s:textfield 
                                name="etax.glAccountNo"
                                size="40"
                                maxlength="6" 
                                cssClass="cls-alphabet-spc ui-widget ui-widget-content"
                                readonly="false"
                                onblur="fetchDebitAccount('GL');"
                                />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <s:label id="lblDebitGLName" key="label.etax.glName" />
                        </td>
                        <td>
                            <s:textfield 
                                name="etax.glAccountName"
                                size="40"
                                maxlength="12" 
                                cssClass="cls-alphabet-spc ui-widget ui-widget-content"
                                readonly="true"
                                />
                        </td>
                    </tr>
                </tbody>
            </table>
        </fieldset>
        <fieldset id="fsDebitCash" class="ui-widget-content ui-corner-all" style="display: none;">
            <legend class="ui-widget-header ui-corner-all"><s:text name="label.etax.fieldset.legend.billing.debitCash" /></legend>
            <table>
                <tbody>
                    <tr>
                        <td>
                            <s:label id="lblDebitCustomerName" key="label.etax.customerName" requiredLabel="true" />
                        </td>
                        <td>
                            <s:textfield 
                                name="etax.cashCustomerName"
                                size="40"
                                maxlength="40" 
                                cssClass="cls-alphabet-spc ui-widget ui-widget-content"
                                readonly="false"
                                cssStyle="text-transform:uppercase"
                                />
                        </td>
                        <td>
                            <s:label id="lblDebitBranchGL" key="label.etax.branchGL"/>
                        </td>
                        <td>
                            <s:textfield 
                                name="etax.cashBranchGL"
                                size="40"
                                maxlength="6" 
                                cssClass="cls-alphabet-spc ui-widget ui-widget-content"
                                readonly="true"
                                />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <s:label id="lblDebitIdType" key="label.etax.idType" requiredLabel="true" />
                        </td>
                        <td colspan="3">
                            <s:url id="getListIdTypes" action="json/32101_listIdTypes"/>
                            <sj:select
                                name="etax.cashIdType"
                                href="%{getListIdTypes}"
                                list="idTypes"
                                cssClass="ui-widget ui-widget-content"
                                emptyOption="true"
                                headerKey="0"
                                />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <s:label id="lblDebitIdNo" key="label.etax.idNumber" requiredLabel="true" />
                        </td>
                        <td colspan="3">
                            <s:textfield 
                                name="etax.cashIdNo"
                                size="40"
                                maxlength="16" 
                                cssClass="cls-alphabet-spc ui-widget ui-widget-content"
                                readonly="false"
                                cssStyle="text-transform:uppercase"
                                />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <s:label id="lblDebitCustomerType" key="label.etax.customerType" requiredLabel="true" />
                        </td>
                        <td colspan="3">                            
                            <s:url id="getListCustomerTypes" action="json/32101_listCustomerTypes"/>
                            <sj:select
                                name="etax.cashCustomerType"
                                href="%{getListCustomerTypes}"
                                list="customerTypes"
                                cssClass="ui-widget ui-widget-content"
                                emptyOption="true"
                                headerKey="0"
                                />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <s:label id="lblDebitCustomerPhone" key="label.etax.customerPhone" requiredLabel="true" />
                        </td>
                        <td colspan="3">
                            <s:textfield 
                                name="etax.cashCustomerPhone"
                                size="40"
                                maxlength="40" 
                                cssClass="cls-alphabet-spc ui-widget ui-widget-content"
                                readonly="false"
                                cssStyle="text-transform:uppercase"
                                />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <s:label id="lblDebitCustomerAddress" key="label.etax.customerAddress" requiredLabel="true" />
                        </td>
                        <td colspan="3">
                            <s:textarea 
                                name="etax.cashCustomerAddress"
                                cols="75"
                                rows="2" 
                                cssClass="cls-alphabet-spc ui-widget ui-widget-content"
                                readonly="false"
                                cssStyle="resize: none; text-transform:uppercase;"
                                />
                        </td>
                    </tr>
                </tbody>
            </table>
        </fieldset>
        <fieldset id="fsCredit" class="ui-widget-content ui-corner-all" style="display: none;">
            <legend class="ui-widget-header ui-corner-all"><s:text name="label.etax.fieldset.legend.billing.credit" /></legend>
            <table>
                <tbody>
                    <tr>
                        <td>
                            <s:label id="lblNomorKPPN" key="label.etax.nomorKPPN" />
                        </td>
                        <td>
                            <s:textfield 
                                name="etax.kppnAccountNo"
                                size="40"
                                cssClass="cls-alphabet-spc ui-widget ui-widget-content"
                                readonly="true"
                                />
                        </td>
                        <td>
                            <s:label id="lblCreditAccountName" key="label.etax.creditAccountName"/>
                        </td>
                        <td>
                            <s:textfield 
                                name="etax.kppnAccountName"
                                size="40"
                                cssClass="cls-alphabet-spc ui-widget ui-widget-content"
                                readonly="true"
                                />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <s:label id="lblTrxCurrency" key="label.etax.trxCurrency" />
                        </td>
                        <td>
                            <s:textfield 
                                name="etax.kppnAccountCcyName"
                                size="40"
                                maxlength="12" 
                                cssClass="cls-alphabet-spc ui-widget ui-widget-content"
                                readonly="true"
                                />
                        </td>
                        <td>
                            <s:label id="lblExchangeRate" key="label.etax.exchangeRate" />
                        </td>
                        <td>
                            <s:textfield 
                                name="etax.exchangeRate"
                                size="40"
                                cssClass="cls-alphabet-spc ui-widget ui-widget-content"
                                readonly="true"
                                />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <s:label id="lblCreditNominalLCE" key="label.etax.creditNominalLCE" />
                        </td>
                        <td>
                            <s:textfield 
                                name="etax.creditNominalLCE"
                                size="40"
                                maxlength="12" 
                                cssClass="cls-alphabet-spc ui-widget ui-widget-content"
                                readonly="true"
                                />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <s:label id="lblDescription" key="label.etax.description" />
                        </td>
                        <td colspan="3">
                            <s:textarea 
                                name="etax.description"
                                cols="75"
                                rows="2" 
                                cssClass="cls-alphabet-spc ui-widget ui-widget-content"
                                readonly="false"
                                cssStyle="resize: none; text-transform:uppercase;"
                                />
                        </td>
                    </tr>
                    <tr></tr>
                    <tr>
                        <td></td>
                        <td>
                            <sj:a id="btnConfirm" button="true" key="button.confirm">Confirm</sj:a>
                            <sj:a id="btnSubmit" button="true" key="button.submit">Submit</sj:a>
                            <sj:a id="btnPaymentCancel" button="true" key="button.cancel">Cancel</sj:a>
                        </td>
                    </tr>
                </tbody>
            </table>
            
        </fieldset>
        <div  id="btnPayment">
            <sj:a id="button_payment" button="true" key="button.confirm">Payment</sj:a>
            <br>
        </div>    
        <div  id="mpnResponseX">               
            <s:include value="/WEB-INF/32102/main.jsp" >
            </s:include>
        </div>
        <s:token />
        <div id="divETaxAkhirLoad"></div>
        <div id="divETaxAkhirMess"></div>
        <s:hidden name="state" value="%{#state}" />
        <s:hidden name="rate" value="%{#etax.exchangeRate}"/>
        <%--<s:hidden name="strData.cifNo" />--%>
    </s:form>
    <s:form id="formValidateAcc" action="32102_main">
        <s:token />
    </s:form>

    <%-- Buttons --%>
    <sj:a id="btnLookupHajiType" button="true">...</sj:a>


        <script type="text/javascript">
            function getLabelText(elementId) {
                return $("label[for='" + elementId + "']").text().replace(/[*:]/g, '').trim();
            }
            function onReloadCurrency() {
                console.log('ON RELOAD CURRENCY!!');
                return false;
            }
            function getMidRate() {
                $('#frmMain_ccyNames').attr('value', $("#frmMain_ccyUd").val());
                $('#formMidRate_ccyName').attr("value", $("#ccyUd").val());
                $("#tempformMidRate").click();
                midParams = {};
                midParams.ccyMidRate = "frmMain_ccyMidRate";
                midParams.reason = "frmMain_reason";
                midParams.onClose = function (ccyName, ccyMidRate, reason) {
                    //if (ccy != "USD")
                    if (reason == '') {
                        alert("GET DATA midrate :" + ccyMidRate);
                        $('#frmMain_ccyMidRate').attr("value", ccyMidRate);
                        $("#frmMain_midRatescr").val($("#frmMain_ccyMidRate").val());
                        $("#frmMain_midRatescr").formatCurrency({decimalSymbol: '.', digitGroupSymbol: ',', symbol: ''});
                        $("#btnSave").button("enable");
                    } else {
                        messageBoxClass(2, 'divMessageCalc', reason, function () {
                            $("#frmMain_midRatescr").val(null);
                            $('#frmMain_reason').attr("values", "");
                        });
                    }
                };
            }
            function fetchDebitAccount(type) {
                switch(type) {
                    case 'CASA':
                        var acctNo = $('#frmMain_etax_debitAccountNo').val();
                        console.log('fetchDebitAccount: [CASA, ' + acctNo + ']');
                        if(acctNo == '') {
                            alert('Nomor Rekening cannot be empty');
                            $("#frmMain_etax_debitAccountNo").focus();
                            return;
                        }
                        fetchAccount(acctNo, 'CASA');
                        break;
                    case 'GL':
                        var glNo = $('#frmMain_etax_glAccountNo').val();
                        console.log('fetchDebitAccount: [GL, ' + glNo + ']');
                        if(glNo == '') {
                            alert('Nomor GL cannot be empty');
                            $("#frmMain_etax_glAccountNo").focus();
                            return;
                        }
                        fetchAccount(glNo, 'GL');
                        break;
                }
            }
            function fetchAccount(accountNo, accountType) {
                $('#formValidateAcc_noAccount').val(accountNo);
                $('#formValidateAcc_typeAccount').val(accountType);
                inqAcct = {};
                inqAcct.onClose = function(acct) {
                    if(acct != null) {
                        if(acct.codAcctTitle != null || acct.namGLCode != null) {
                            if(acct.type == 'CASA') {
                                $('#frmMain_etax_debitAccountName').val(acct.codAcctTitle);
                            } else if(acct.type == 'GL') {
                                $('#frmMain_etax_glAccountName').val(acct.namGLCode);
                            }
                        } else {
                            if(acct.type == 'CASA') {
                                $('#frmMain_etax_debitAccountName').val('');
                                $('#formValidateAcc_noAccount').focus();
                            } else if(acct.type == 'GL') {
                                $('#frmMain_etax_glAccountName').val('');
                                $('#formValidateAcc_typeAccount').focus();
                            }
                            messageBoxClass(1, "divETaxAkhirMess", 'Nomor Rekening tidak ditemukan!', function () {
                                //$("#ph-main").scrollTop(0);
                            });
                        }
                    } else {
                        $('#frmMain_etax_debitAccountName').val('');
                        $('#frmMain_etax_glAccountName').val('');
                        messageBoxClass(1, "divETaxAkhirMess", 'Nomor Rekening tidak ditemukan!', function () {
                            //$("#ph-main").scrollTop(0);
                        });
                    }
                    $("div[role='dialog']").find("div[id='divETaxAkhir']")
                        .dialog("close")
                        .dialog("destroy");
                };
                var messaging = "Please Waiting Your Request . . . . .";
                waitingMessage(3, messaging, "divETaxAkhir");
                $("#tempValidateAcc").click();
            };
            function getDataText(elementId, appendText) {
                var $elem = $("#" + elementId);
                var value = "";
                if ($elem.length == 1) {
                    if (($elem.prop("tagName") == "INPUT") && (($elem.prop("type") == "text") || ($elem.prop("type") == "hidden")))
                        value = $("#" + elementId).val().trim();
                    else if ($elem.prop("tagName") == "TEXTAREA")
                        value = $("#" + elementId).val().trim();
                    else if ($elem.prop("tagName") == "SELECT")
                        value = $("#" + elementId + " option:selected").text().trim();
                }
                if (arguments.length > 1)
                    value += (((value.length > 0) ? " " : "") + appendText.trim());
                return (value == "") ? "-" : value;
            }

            function setView(viewState, globalState) {
                var currGlobalState = $("#frmView_globalState").val();
                var currState = getState();
                //console.log('currState: ' + currState + ', viewState: ' + viewState + ', currGlobalState: ' + currGlobalState + ', globalState: ' + globalState);
                if(currState == viewState && currGlobalState == globalState) {
                    //console.log('-----> IGNORED!!');
                    return;
                }
                console.log('###setView called [\'' + viewState + ', ' + globalState + '\']');
                $("#frmView_globalState").val(globalState);
                switch(viewState) {
                    case 0:
                        $("#frmView_formId").val('frmPayment');
                        break;
                    case 1:
                        $("#frmView_formId").val('frmReinquiry');
                        break;
                }
                _setViewState($("#frmView_formId").val(), globalState);
                
                //console.log('View State After Set: ' + getState());
            }
            function _setViewState(formId, state) {
                if(formId === 'frmPayment') {
                    setViewStateReinquiry(-1);
                    setViewState(state);
                } else if(formId === 'frmReinquiry') {
                    setViewState(-1);
                    setViewStateReinquiry(state);
                }
            }
            function setViewState(state) {
                var paymentType = $.trim($('#frmPayment_paymentType').val());
                console.log('Set View State: ' + state);
                setState(state);
                if(state == -1) {
                    $('#fsDataETax').hide();
                    $('#fsDebitCASA').hide();
                    $('#fsDebitGL').hide();                    
                    $('#fsDebitCash').hide();
                    $('#fsCredit').hide();
                    $('#mpnResponseX').hide();
                    $('#btnPayment').hide();
                    $('#fsInquiry').attr('disabled', 'disabled');
                    $('#fsInquiry').hide();
                } else if(state == 0) {
                    $('#frmPayment').trigger('reset');
                    
                    $('#fsDataETax').removeAttr('disabled');
                    $('#fsDebitCASA').removeAttr('disabled');
                    $('#fsDebitGL').removeAttr('disabled');
                    $('#fsDebitCash').removeAttr('disabled');
                    $('#fsCredit').removeAttr('disabled');
                    
                    $('#btnOk').removeAttr('disabled');
                    $('#btnCancel').removeAttr('disabled');
                    
                    $('#fsDataETax').hide();
                    $('#fsDebitCASA').hide();
                    $('#fsDebitGL').hide();
                    $('#fsDebitCash').hide();
                    $('#fsCredit').hide();
                    $('#mpnResponseX').hide();
                    $('#btnPayment').hide();
                    $('#fsInquiry').removeAttr('disabled');
                    $('#fsInquiry').show();
                    // buttons
                    $('#btnConfirm').hide();
                    $('#btnSubmit').hide();
                    $('#btnPaymentCancel').hide();
                } else if(state == 1) {
                    $('#fsDataETax').attr('disabled', 'disabled');
                    $('#fsDebitCASA').attr('disabled', 'disabled');
                    $('#fsDebitGL').attr('disabled', 'disabled');
                    $('#fsDebitCash').attr('disabled', 'disabled');
                    $('#fsCredit').attr('disabled', 'disabled');
                    $('#fsInquiry').attr('disabled', 'disabled');
                    
                    $('#btnOk').attr('disabled', 'true');
                    $('#btnCancel').attr('disabled', 'true');
                    
                    // buttons
                    $('#btnConfirm').hide();
                    $('#btnSubmit').show();
                    $('#btnPaymentCancel').show();
                } else if(state == 99) {
                    //$('#frmPayment').trigger('reset');
                    // just hide all details but don't reset
                    $('#fsDataETax').hide();
                    $('#fsDebitCASA').hide();
                    $('#fsDebitGL').hide();                    
                    $('#fsDebitCash').hide();
                    $('#fsCredit').hide();
                    $('#mpnResponseX').hide();
                    $('#btnPayment').hide();
                    $('#fsInquiry').removeAttr('disabled');
                    $('#fsInquiry').show();
                } else if(state == 11 || state == 12 || state == 13) {
                    $('#fsDataETax').show();
                    if(state == 11) {
                        $('#fsBillingDetailsDJP').show();
                        $('#fsBillingDetailsDJBC').hide();
                        $('#fsBillingDetailsDJA').hide();
                        
                        $("label[for='lblWPName']").text('Nama Wajib Pajak:');
                        $("#tableBillingInfo tr:nth-child(2)").show();
                        $("#tableBillingInfo tr:nth-child(4)").hide();
                        $("#tableBillingInfo tr:nth-child(7)").show();
                    } else if(state == 12) {
                        $('#fsBillingDetailsDJBC').show();
                        $('#fsBillingDetailsDJP').hide();
                        $('#fsBillingDetailsDJA').hide();
                        
                        $("label[for='lblWPName']").text('Nama Wajib Bayar:');
                        $("#tableBillingInfo tr:nth-child(2)").hide();
                        $("#tableBillingInfo tr:nth-child(4)").show();
                        $("#tableBillingInfo tr:nth-child(7)").hide();
                    } else if(state == 13) {
                        $('#fsBillingDetailsDJA').show();
                        $('#fsBillingDetailsDJBC').hide();
                        $('#fsBillingDetailsDJP').hide();
                        
                        $("label[for='lblWPName']").text('Nama Wajib Bayar:');
                        $("#tableBillingInfo tr:nth-child(2)").hide();
                        $("#tableBillingInfo tr:nth-child(4)").hide();
                        $("#tableBillingInfo tr:nth-child(7)").hide();
                    }
                    // payment type
                    if(paymentType === '2') {
                        $('#fsDebitCASA').show();
                        $('#fsDebitGL').hide();
                        $('#fsDebitCash').hide();
                    } else if(paymentType === '3') {
                        $('#fsDebitCASA').hide();
                        $('#fsDebitGL').show();
                        $('#fsDebitCash').hide();
                    } else if(paymentType === '1') {
                        $('#fsDebitCASA').hide();
                        $('#fsDebitGL').hide();
                        $('#fsDebitCash').show();
                    }
                    //$('#fsDebitCASA').show();
                    $('#fsCredit').show();
                    $('#btnPayment').show();
                    $('#fsInquiry').show();
                    
                    // buttons
                    $('#btnConfirm').show();
                    $('#btnSubmit').hide();
                    $('#btnPaymentCancel').show();
                }
            }
            function setViewStateReinquiry(state) {
                switch (state) {
                    case -1:
                        $('#fsReinquiry').attr('disabled', 'disabled');
                        $('#fsReinquiry').hide();
                        break;
                    case 0:
                        $('#frmReinquiry').trigger('reset');
                        
                        $('#fsReinquiry').removeAttr('disabled');
                        $('#fsReinquiry').show();
                        break;
                }
            }
            function getState() {
                return $("#frmMain_state").val();
            }
            function setState(state) {
                $("#frmMain_state").val(state);
            }
            function getSupposedState() {
                return '1';
            }
            jQuery(document).ready(function () {
                var currentState = <s:property value="%{state}" />;
                setState(currentState);
                console.log('Current State: ' + currentState);
                $('#mpnResponseX').hide();
                $('#btnPayment').hide();
                /* === [BEGIN] event hook === */
                $("#btnOk").unsubscribe("click");
                $("#btnOk").subscribe("click", function (event) {
                    event.originalEvent.defaultPrevented = true;
                    //console.log('BTN OK CLICKED!!');
                    //TODO: clearResult();
                    $("#errMsg").html("");
                    
                    var billingId = $.trim($('#frmPayment_billingId').val());
                    var paymentType = $.trim($('#frmPayment_paymentType').val());
                    if (billingId == '') {
                        messageBoxClass(1, "divETaxAkhirMess", 'Must Fill Billing ID, Payment Type', function () {
                            $("#ph-main").scrollTop(0);
                        });
                        $('#frmPayment_billingId').focus();
                        return;
                    } else if (paymentType == '') {
                        messageBoxClass(1, "divETaxAkhirMess", 'Must Select Payment Type', function () {
                            $("#ph-main").scrollTop(0);
                        });
                        $('#frmPayment_paymentType').focus();
                        return;
                    } else {
                        $('#formData_billingId').attr('value', $('#frmPayment_billingId').val());
                        $('#formData_paymentType').attr('value', $('#frmPayment_paymentType').val());
                        $('#tempformData').click();
                        var messaging = "Please Waiting Your Request . . . . .";
                        waitingMessage(3, messaging, "divETaxAkhir");

                        inqParam = {};
                        inqParam.onClose = function (etax) {
                            var responseCode = etax['responseCode'];
                            if(responseCode != undefined && parseInt(responseCode) == 0) {
                                // set data
                                for(var propt1 in etax){
                                    if(propt1 == 'debitAccountNo' ||
                                            propt1 == 'debitAccountName' ||
                                            propt1 == 'glAccountNo' ||
                                            propt1 == 'glAccountName') {
                                        continue;
                                    }
                                    console.log("FIELD 1 :" + propt1 + " " + etax[propt1]);
                                    $("#frmMain_etax_" + propt1).val(etax[propt1]);
                                }
                                var billingInfo = etax['billingInfo'];
                                if(billingInfo != null) {
                                    for(var propt2 in billingInfo){
                                        console.log("FIELD 2 :" + propt2 + " " + billingInfo[propt2]);
                                        $("#frmMain_etax_billingInfo_" + propt2).val(billingInfo[propt2]);
                                    }
                                }
                                var rate = etax['exchangeRate'];
                                $('#frmMain_rate').val(rate);
                                // calculate LCE
                                //var amountLCE = etax['amount'] * rate;
                                //$('#frmMain_etax_nominalLCE').val(amountLCE);
                                
                                var billingIdPref = billingId.substring(0, 1);
                                switch(billingIdPref) {
                                    case '0':
                                    case '1':
                                    case '2':
                                    case '3':
                                        setView(0, 11);
                                        break;
                                    case '4':
                                    case '5':
                                    case '6':
                                        setView(0, 12);
                                        break;
                                    case '7':
                                    case '8':
                                    case '9':
                                        setView(0, 13);
                                        break;
                                }
                            } else {
                                setView(0, 99);
                                var responseMsg = etax['responseDesc'];
                                $("#errMsg").html("<ul id=\"actionError\" class=\"errorMessage\"><li><span>"+responseMsg+"</span></li></ul>");
                            }

                            $("div[role='dialog']").find("div[id='divETaxAkhir']")
                                    .dialog("close")
                                    .dialog("destroy");
                        };
                    }
                });
                $("#btnCancel").unsubscribe("click");
                $("#btnCancel").subscribe("click", function (event) {
                    event.originalEvent.defaultPrevented = true;
                    console.log('btnCancel triggered');
                    setView(0, 0);
                });
                $("#btnPaymentCancel").unsubscribe("click");
                $("#btnPaymentCancel").subscribe("click", function (event) {
                    event.originalEvent.defaultPrevented = true;
                    messageBoxOkCancel2(3, "divETaxAkhirMess", '<s:text name="32101.cancel.confirmText" />', function () {
                        setView(0, 0);
                    }, function() {
                        // no-op
                    });
                });
                $("#btnReinqClear").unsubscribe("click");
                $("#btnReinqClear").subscribe("click", function (event) {
                    event.originalEvent.defaultPrevented = true;
                    console.log('btnReinqClear triggered');
                    setView(1, 0);
                });
                
                $("#btnConfirm").unsubscribe("click");
                $("#btnConfirm").subscribe("click", function (event) {
                    event.originalEvent.defaultPrevented = true;
                    console.log('btnConfirm triggered');
                    setView(0, 1);
                });
                $("#btnSubmit").unsubscribe("click");
                $("#btnSubmit").subscribe("click", function (event) {
                    event.originalEvent.defaultPrevented = true;
                    messageBoxOkCancel2(3, "divETaxAkhirMess", '<s:text name="32101.confirmation.confirmText" />', function () {
                        console.log('CONFIRM OK');
                    }, function() {
                        console.log('CANCEL OK');
                    });
                });
                /* === [BEGIN] alter display === */

                $wtg = $("div[role='dialog']").find("div[id='divETaxAkhir']");
                $wtg.dialog("close");
                $wtg.dialog("destroy");
                $wtg.remove();

                $wtg = $("div[role='dialog']").find("div[id='divETaxAkhirLoad']");
                $wtg.dialog("close");
                $wtg.dialog("destroy");
                $wtg.remove();

                $("#ph-main").find("div[id^='wwctrl_lbl']").remove(); /* because tag s:label generate double text */
                $("#frmPayment, #frmMain, #frmReinquiry").find('.wwlbl').attr('style', 'width:100%;float:left;text-align:right;vertical-align:middle');
                $("#frmPayment, #frmMain, #frmReinquiry").find("div[id^='wwgrp_lbl']").parent().width(165);
                $("#fsBillingDetailsDJBC").find("div[id^='wwgrp_lbl']").parent().width(180);
                $("#frmPayment_acctNoInq").parent().append('&nbsp;').append($("#btnSearchInq").detach());
                $("#frmPayment_hajiTypeDesc").parent().append('&nbsp;').append($("#btnLookupHajiType").detach());
                $("#frmMain, #frmReinquiry").find("table > tbody > tr > td:nth-child(2)").css("min-width", "260px");
                $("#frmMain, #frmReinquiry").find("tbody").children().css("height", "26px");
                $("#frmMain > fieldset > legend, #frmReinquiry > fieldset > legend")
                        .css("margin-left", "10px")
                        .css("text-align", "center")
                        .css("width", function () {
                            var oldWidth = parseInt($(this).css("width"), 10);
                            var width_ = ((oldWidth + 25) + "px");
                            if(oldWidth === 0) {
                                width_ = "";
                            }
                            return width_;
                        });
                $(".ui-autocomplete-input", $("#frmMain")).addClass("ui-widget ui-widget-content");

                $("#frmPayment_svdp_statusInq").attr("id", "frmMain_svdp_statusInq"); // change id attribute of statusInq

                filterKeysNumeric();

                //tittle 32101
                if (currentState == 0) {
                    $("#rbBS").buttonset("destroy");
                    $("#tempTitle").click();
                    $("#tempButtons").click();
                } else {
                    if (currentState > "0") {
                        $("#rbBS").data("rdb").callCurrent();
                        <s:if test="%{#request.SKHT_MESSAGE.equals('') == false}">
                        messageBoxClass(3, "divETaxAkhirMess", '<s:property value="%{#request.SKHT_MESSAGE}" />');
                        </s:if>
                        if (currentState == "2") {
                            $("#btnGeneratePDF").button("enable");
                        }
                        console.log('STATE: ' + currentState);
                    }
                }
                $("#button_payment").unsubscribe("click");
                $("#button_payment").subscribe("click", function (event) {
                        $('#formPosting_billingId').attr('value', $('#frmPayment_billingId').val());
                        $('#formPosting_paymentType').attr('value', $('#frmPayment_paymentType').val());
                        $('#tempformPosting').click();
                        var messaging = "Please Waiting Your Request . . . . .";
                        waitingMessage(3, messaging, "divETaxAkhirLoad");
                        $('#mpnResponseX').show();
                        $("div[role='dialog']").find("div[id='divETaxAkhirLoad']")
                                    .dialog("close")
                                    .dialog("destroy");
                });
            });

            function myDialog(eButton, title, eDialog, eId, eDesc, closeFunction) {
                return function () {
                    if (!(
                            (eButton.button('option').disabled != undefined) &&
                            (eButton.button('option', 'disabled'))
                            )) {
                        dlgParams = {};
                        dlgParams.id = eId;
                        dlgParams.desc = eDesc;

                        var $tmp = $("#ph-dlg").dialog("option", "title");
                        $("#ph-dlg").dialog("option", "position", "center");
                        $("#ph-dlg").dialog("option", "width", ($("body").width() * (3 / 4)));
                        $("#ph-dlg").dialog("option", "height", 450);

                        $("#ph-dlg")
                                .html("Please wait...")
                                .unbind("dialogopen")
                                .bind("dialogopen", function () {
                                    $("#" + eDialog).click();
                                })
                                .unbind("dialogclose")
                                .bind("dialogclose", function () {
                                    $(this).dialog({title: $tmp});
                                    if (closeFunction != undefined)
                                        closeFunction();
                                })
                                .dialog({
                                    title: title
                                })
                                .dialog("open");
                    }
                };
            }
            ;
    </script>
</s:if>