package org.protege.editor.owl.codegeneration;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.JFrame;

import org.protege.editor.owl.model.OWLModelManager;
import org.protege.editor.owl.ui.action.ProtegeOWLAction;
import org.semanticweb.HermiT.Reasoner;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;

/**
 * @author z.khan
 *
 */
public class GenerateProtegeOwlJavaCodeAction extends ProtegeOWLAction implements GenerateCodeWithOptions {

    private static final long serialVersionUID = 1L;
    
    EditableJavaCodeGeneratorOptionsImpl options;

    private JFrame codeGenOptionFrame;

    public void initialise() throws Exception {

    }

    public void dispose() throws Exception {

    }

    public void actionPerformed(ActionEvent e) {
        showGeneratorPanel();
    }

    private void showGeneratorPanel() {
        options = new EditableJavaCodeGeneratorOptionsImpl();
        JavaCodeGeneratorPanel javaCodeGeneratorPanel = new JavaCodeGeneratorPanel(options, this);
        codeGenOptionFrame = new JFrame("Generate Protege-OWL Java Code");
        codeGenOptionFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        codeGenOptionFrame.add(javaCodeGeneratorPanel);
        codeGenOptionFrame.setSize(300, 350);
        codeGenOptionFrame.setVisible(true);
        center(codeGenOptionFrame);
    }

    public static void center(Component c) {
        Dimension screenSize = c.getToolkit().getScreenSize();
        int BORDER_SIZE = 50;
        screenSize.width -= BORDER_SIZE;
        screenSize.height -= BORDER_SIZE;
        Dimension componentSize = c.getSize();
        int xPos = (screenSize.width - componentSize.width) / 2;
        xPos = Math.max(xPos, 0);
        int yPos = (screenSize.height - componentSize.height) / 2;
        yPos = Math.max(yPos, 0);
        c.setLocation(new Point(xPos, yPos));
    }

    public void okClicked() {
        codeGenOptionFrame.setVisible(false);
        OWLModelManager owlModelManager = getOWLModelManager();
        OWLOntology owlOntology = owlModelManager.getActiveOntology();
        OWLReasonerFactory reasonerFactory = new Reasoner.ReasonerFactory();
        OWLReasoner reasoner = reasonerFactory.createReasoner(owlOntology);
        JavaCodeGenerator javaCodeGenerator = new JavaCodeGenerator(owlOntology,options);
        OWLOntologyID owlOntologyID = owlOntology.getOntologyID();
//        OWLDataFactory fac = owlModelManager.getOWLDataFactory();
//        javaCodeGenerator.setOwlDataFactory(fac);
        IRI iri = owlOntologyID.getOntologyIRI();
        javaCodeGenerator.setIRI(iri);

        try {
            javaCodeGenerator.createAll(reasoner);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cancelClicked() {
        codeGenOptionFrame.setVisible(false);
    }
}