package bdsm.scheduler.processor;

import bdsm.model.RekeningKoran;
import bdsm.model.RekeningKoranFooterArea;
import bdsm.scheduler.MapKey;
import bdsm.scheduler.PropertyPersister;
import bdsmhost.dao.RekeningKoranDAO;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.dom4j.Document;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

/**
 *
 * @author Jaka
 */
public class RekeningKoranWorker extends BaseProcessor {

    public RekeningKoranWorker(Map context) {
        super(context);
    }

    public String generate(List<RekeningKoran> rekKoran, RekeningKoranFooterArea footerArea) throws Exception {
        try {
            if (rekKoran == null) {
                throw new RuntimeException("Null AppArea!");
            }
            String reportFilename = context.get(MapKey.reportFileName).toString();
            String outputFile = reportFilename;

            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
            DecimalFormat df1 = new DecimalFormat("###");

            StringWriter sw = new StringWriter();
            DocumentFactory factory = DocumentFactory.getInstance();
            //use the factory to create a root element
            Element rootElement = factory.createElement("SOAP-ENV:Envelope");
            //use the factory to create a new document with the previously created root element
            Document doc = factory.createDocument(rootElement);

            //create some dom4j namespaces that we like to add to our new document
            Namespace namespace = new Namespace("SOAP-ENV", "http://schemas.xmlsoap.org/soap/envelope/");
            if (rekKoran != null && rekKoran.size() > 0) {
                //add the created namespaces to the document</span>
                rootElement.add(namespace);

                rootElement.addElement("SOAP-ENV:Header");
                Element bodyElement = rootElement.addElement("SOAP-ENV:Body");
                Element bankStatementElement = bodyElement.addElement("BANK_STATEMENT");

                Element applicationAreaElement = bankStatementElement.addElement("ApplicationArea");

                applicationAreaElement.addElement("ApplicationAreaSenderIdentifier").setText(rekKoran.get(0).getSenderName());
                applicationAreaElement.addElement("ApplicationAreaReceiverIdentifier").setText(rekKoran.get(0).getReceiverName());
                applicationAreaElement.addElement("ApplicationAreaDetailSenderIdentifier");
                applicationAreaElement.addElement("ApplicationAreaDetailReceiverIdentifier");
                applicationAreaElement.addElement("ApplicationAreaCreationDateTime").setText(rekKoran.get(0).getCreationDateTime());
                applicationAreaElement.addElement("ApplicationAreaMessageIdentifier").setText(rekKoran.get(0).getMessageIdentifier());
                applicationAreaElement.addElement("ApplicationAreaMessageTypeIndicator").setText(rekKoran.get(0).getMessageType());
                applicationAreaElement.addElement("ApplicationAreaMessageVersionText").setText(rekKoran.get(0).getVersionText());

                Element headerAreaElement = bankStatementElement.addElement("HeaderArea");
                headerAreaElement.addElement("BankCode").setText(rekKoran.get(0).getBankCode());
                headerAreaElement.addElement("BankAccountNumber").setText(rekKoran.get(0).getBankAccountNo());
                headerAreaElement.addElement("BankStatementDate").setText(sdf2.format(rekKoran.get(0).getBankStatementDate()));
                headerAreaElement.addElement("Currency").setText(rekKoran.get(0).getCurrency());
                headerAreaElement.addElement("BeginningBalance").setText(df1.format(rekKoran.get(0).getBeginningBalance()));
                headerAreaElement.addElement("EndingBalance").setText(df1.format(rekKoran.get(0).getEndingBalance()));


                for (RekeningKoran bodyArea : rekKoran) {
                    Element lineAreaElement = bankStatementElement.addElement("LineArea");
                    lineAreaElement.addElement("LineNumber").setText(bodyArea.getLineNumber());
                    lineAreaElement.addElement("BankTransactionCode").setText(bodyArea.getBankTransactionCode());
                    lineAreaElement.addElement("DebitCredit").setText(bodyArea.getDebitCredit());
                    lineAreaElement.addElement("BankReferenceNumber").setText(bodyArea.getBankReferenceNumber());
                    lineAreaElement.addElement("TransactionDate").setText(sdf2.format(bodyArea.getTransactionDate()));
                    lineAreaElement.addElement("ValueDate").setText(sdf2.format(bodyArea.getValueDate()));
                    lineAreaElement.addElement("OriginalAmount").setText(df1.format(bodyArea.getOriginalAmount()));
                }
                Element footerElement = bankStatementElement.addElement("Footer");
                footerElement.addElement("TotalCount").setText(df1.format(footerArea.getTotalCount()));
                footerElement.addElement("TotalAmount").setText(df1.format(footerArea.getTotalAmount()));
            }
            // Flush after done
            OutputFormat outformat = OutputFormat.createPrettyPrint();
            XMLWriter writer = new XMLWriter(sw, outformat);
            writer.write(doc);
            writer.flush();
            sw.flush();
            Writer out = new OutputStreamWriter(new FileOutputStream(new File(PropertyPersister.dirFixOut, outputFile)));
            out.write(sw.toString());
            out.close();
        } catch (Exception e) {
            this.getLogger().info("exception generate rekeningKoran.txt : " + e);
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected boolean doExecute() throws Exception {
        try {
            RekeningKoranWorker gen = new RekeningKoranWorker(context);
            List<RekeningKoran> rekKoran = new LinkedList<RekeningKoran>();
            RekeningKoranDAO dao = new RekeningKoranDAO(session);
            String acctNo = PropertyPersister.codAcctNo;
            String startDate = context.get(MapKey.param1).toString();
            String endDate = context.get(MapKey.param2).toString();
            rekKoran = dao.listRekKoran(acctNo, startDate, endDate);

            this.getLogger().info("REK WORKING LIST : " + rekKoran.size());

            BigDecimal totalAmount = BigDecimal.ZERO;
            for (RekeningKoran koran : rekKoran) {
                totalAmount = totalAmount.add(koran.getOriginalAmount());
            }
            // footer area
            RekeningKoranFooterArea footerArea = new RekeningKoranFooterArea();
            footerArea.setTotalCount(rekKoran.size());
            footerArea.setTotalAmount(totalAmount);

            gen.generate(rekKoran, footerArea);

        } catch (Exception e) {
            this.getLogger().info("exception make rekeningKoran.txt : " + e);
            e.printStackTrace();
        }
        return true;
    }
}
