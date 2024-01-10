/*
 * Formato de mensajes para datagrama sobre UDP. Es muy similar al BlueMessage, pero no
 * tiene los campos de sender y receiver porque estos ya van incluidos en el datagram.
 */


/************************************************************
 * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * IMPORTANTE
 * El Mensaje AuroraMessage no puede ocupar mas de 1024 bytes
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * ***********************************************************/
package org.sunspotworld;

import java.util.StringTokenizer;
import java.util.Vector;



/**
 *
 * @author Inma
 */
public class AuroraMessage {
    
    public static final String ACCEPT_PROPOSAL = "ACCEPT_PROPOSAL";
	/** constant identifying the FIPA performative **/
	public static final String AGREE = "AGREE";
	/** constant identifying the FIPA performative **/
	public static final String CANCEL = "CANCEL";
	/** constant identifying the FIPA performative **/
	public static final String CFP = "CFP";
	/** constant identifying the FIPA performative **/
	public static final String CONFIRM = "CONFIRM";
	/** constant identifying the FIPA performative **/
	public static final String DISCONFIRM = "DISCONFIRM";
	/** constant identifying the FIPA performative **/
	public static final String FAILURE = "FAILURE";
	/** constant identifying the FIPA performative **/
	public static final String INFORM = "INFORM";
	/** constant identifying the FIPA performative **/
	public static final String INFORM_IF = "INFORM_IF";
	/** constant identifying the FIPA performative **/
	public static final String INFORM_REF = "INFORM_REF";
	/** constant identifying the FIPA performative **/
	public static final String NOT_UNDERSTOOD = "NOT_UNDERSTOOD";
	/** constant identifying the FIPA performative **/
	public static final String PROPOSE = "PROPOSE";
	/** constant identifying the FIPA performative **/
	public static final String QUERY_IF = "QUERY_IF";
	/** constant identifying the FIPA performative **/
	public static final String QUERY_REF = "QUERY_REF";
	/** constant identifying the FIPA performative **/
	public static final String REFUSE = "REFUSE";
	/** constant identifying the FIPA performative **/
	public static final String REJECT_PROPOSAL = "REJECT_PROPOSAL";
	/** constant identifying the FIPA performative **/
	public static final String REQUEST = "REQUEST";
	/** constant identifying the FIPA performative **/
	public static final String REQUEST_WHEN = "REQUEST_WHEN";
	/** constant identifying the FIPA performative **/
	public static final String REQUEST_WHENEVER = "REQUEST_WHENEVER";
	/** constant identifying the FIPA performative **/
	public static final String SUBSCRIBE = "SUBSCRIBE";
	/** constant identifying the FIPA performative **/
	public static final String PROXY = "PROXY";
	/** constant identifying the FIPA performative **/
	public static final String PROPAGATE = "PROPAGATE";
	/** constant identifying an unknown performative **/
	public static final String UNKNOWN = "UNKNOWN";

    static String createAuroraMessage(String input) {
        StringTokenizer st = new StringTokenizer(input,"|");
        /*String sender = st.nextToken();
        if(sender.contains("(SENDER ")){
            AuroraMessage msg = new AuroraMessage(sender);
            sender = msg.getSender();
        }*/
        return "(AURORA-MESSAGE  (RECEIVER "+st.nextToken()+") (LANGUAGUE ENGLISH) (CONTENT "+st.nextToken()
                +") (CONVERSATIONID ConverstaionID-12345) (ENCONDING UTF) (PERFORMATIVE PERF) (PROTOCOL "+st.nextToken()
                +") (ONTOLOGY "+st.nextToken()+") (SENDER "+st.nextToken()+"))";
    }

    private String content,converstionId,enconding,language,performative,protocol,sender,ontology;//,
    private Vector receiver;

    public AuroraMessage(){
        receiver=new Vector();
        content="";
    }

    // En el constructor se rellenan todos los campos del mensaje.
    public AuroraMessage(String message){
        if(message.startsWith("@")){
            message = AuroraMessage.createAuroraMessage(message.substring(1));
        }
        
        receiver=new Vector();
        
        StringTokenizer st=new StringTokenizer(message," ");
        String aux;
        // Aquí se adquiere (SL-MESSAGE
        aux=st.nextToken();
        // Se extraen los receiver, siempre tiene que haber por lo menos 1.
        //aux=(RECEIVER
        aux=st.nextToken();
        // El primer receiver
        aux=st.nextToken();
        while(!aux.equals("(LANGUAGUE")){
            this.receiver.addElement(aux);
            aux=st.nextToken();
        }
        // El último de los elementos tiene un ")" en el final que se puede quitar
        // facilmente
        if(receiver.size()>0){
            aux=(String) this.receiver.elementAt(receiver.size()-1);
            aux=aux.substring(0, aux.length()-1);
            // Se sustituye el elemento
            this.receiver.removeElementAt(receiver.size()-1);
            this.receiver.addElement(aux);
        }
        // Se obtiene el SENDER -> la cabecera "(SENDER" ya fue extraida
//        aux=st.nextToken();
//        this.sender=aux.substring(0, aux.length()-1);
        // Se obtiene el lenguaje
        // (LANGUAGUE
        //aux=st.nextToken();
        aux=st.nextToken();
        this.language=aux.substring(0, aux.length()-1);
        // Se obtiene el contenido -> es parecido a extraer a los receptores.
        // (CONTENT
        aux=st.nextToken();
        //System.out.println(aux);
        content=st.nextToken();
        aux=st.nextToken();
        //System.out.println(content);
        while(!aux.equals("(CONVERSATIONID")){
            content=content+" "+aux;
            aux=st.nextToken();
            //System.out.println(content);
        }
        //aux=st.nextToken();
        this.content=content.substring(0, content.length()-1);
        //System.out.println("El contenido es "+content);
        // Se obtiene el conversationID
        //aux=st.nextToken();
        aux=st.nextToken();
        this.converstionId=aux.substring(0, aux.length()-1);
        // Se obtiene el encoding
        aux=st.nextToken();
        aux=st.nextToken();
        this.enconding=aux.substring(0, aux.length()-1);
        // Se obtiene la performativa
        aux=st.nextToken();
        aux=st.nextToken();
        this.performative=aux.substring(0, aux.length()-1);
        // Se obtiene el protocolo, este tiene 2 paréntesis al final
        aux=st.nextToken();
        aux=st.nextToken();
        this.protocol=aux.substring(0, aux.length()-1);
        // Se obtiene la ontolog�a
        aux=st.nextToken();
        aux=st.nextToken();
        this.ontology=aux.substring(0, aux.length()-1);
        aux=st.nextToken();
        aux=st.nextToken();
        this.sender=aux.substring(0, aux.length()-2);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getConverstionId() {
        return converstionId;
    }

    public void setConverstionId(String converstionId) {
        this.converstionId = converstionId;
    }

    public String getEnconding() {
        return enconding;
    }

    public void setEnconding(String enconding) {
        this.enconding = enconding;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getPerformative() {
        return performative;
    }

    public void setPerformative(String performative) {
        this.performative = performative;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }


    
    public String getOntology(){
        return this.ontology;
    }
    
    public void setOntology(String ont){
        this.ontology=ont;
    }
    
    public Vector getReceivers(){
        return this.receiver;
    }
    
    public void setReceivers(Vector rec){
        this.receiver=rec;
    }
    
    public void addReceiver(String rec){
        this.receiver.addElement(rec);
    }
    
    public void removeReceiver(String rec){
        this.receiver.removeElement(rec);
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
    
    // Un reply es un mensaje igual excepto en sender y el receiver.
    public AuroraMessage createReply(){
        AuroraMessage reply=new AuroraMessage();
        
        Vector<String> rec=new Vector<String>();
        rec.add(this.getSender());
        reply.setReceivers(rec);
        reply.setContent("TO-FILL");
        reply.setConverstionId(this.getConverstionId());
        reply.setEnconding(enconding);
        reply.setLanguage(language);
        reply.setOntology(this.getOntology());
        reply.setPerformative(AuroraMessage.ACCEPT_PROPOSAL);
        reply.setProtocol(protocol);
        
        return reply;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AuroraMessage other = (AuroraMessage) obj;
        if ((this.content == null) ? (other.content != null) : !this.content.equals(other.content)) {
            return false;
        }
        if ((this.converstionId == null) ? (other.converstionId != null) : !this.converstionId.equals(other.converstionId)) {
            return false;
        }
        if ((this.enconding == null) ? (other.enconding != null) : !this.enconding.equals(other.enconding)) {
            return false;
        }
        if ((this.language == null) ? (other.language != null) : !this.language.equals(other.language)) {
            return false;
        }
        if ((this.performative == null) ? (other.performative != null) : !this.performative.equals(other.performative)) {
            return false;
        }
        if ((this.protocol == null) ? (other.protocol != null) : !this.protocol.equals(other.protocol)) {
            return false;
        }
        if ((this.sender == null) ? (other.sender != null) : !this.sender.equals(other.sender)) {
            return false;
        }
        if ((this.ontology == null) ? (other.ontology != null) : !this.ontology.equals(other.ontology)) {
            return false;
        }
        if (this.receiver != other.receiver && (this.receiver == null || !this.receiver.equals(other.receiver))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + (this.content != null ? this.content.hashCode() : 0);
        hash = 41 * hash + (this.converstionId != null ? this.converstionId.hashCode() : 0);
        hash = 41 * hash + (this.enconding != null ? this.enconding.hashCode() : 0);
        hash = 41 * hash + (this.language != null ? this.language.hashCode() : 0);
        hash = 41 * hash + (this.performative != null ? this.performative.hashCode() : 0);
        hash = 41 * hash + (this.protocol != null ? this.protocol.hashCode() : 0);
        hash = 41 * hash + (this.sender != null ? this.sender.hashCode() : 0);
        hash = 41 * hash + (this.ontology != null ? this.ontology.hashCode() : 0);
        hash = 41 * hash + (this.receiver != null ? this.receiver.hashCode() : 0);
        return hash;
    }

    // Finalmente esto 
    public String toString(){
        String rec="";
        for(int i=0;i<this.receiver.size();i++){
            rec=rec+" "+this.receiver.elementAt(i);
        }
        
        return "(AURORA-MESSAGE  (RECEIVER"+rec+") (LANGUAGUE "+this.language
                +") (CONTENT "+this.content
                +") (CONVERSATIONID "+this.converstionId+") (ENCONDING "+this.enconding
                +") (PERFORMATIVE "+this.performative+") (PROTOCOL "+this.protocol
                +") (ONTOLOGY "+this.ontology+") (SENDER "+this.sender+"))";
    }
}
