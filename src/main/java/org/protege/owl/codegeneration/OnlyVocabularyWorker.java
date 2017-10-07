package org.protege.owl.codegeneration;

import java.io.File;
import java.io.IOException;

import org.protege.owl.codegeneration.inference.CodeGenerationInference;
import org.protege.owl.codegeneration.names.CodeGenerationNames;
import org.semanticweb.owlapi.model.OWLOntology;

public class OnlyVocabularyWorker extends DefaultWorker {

    public OnlyVocabularyWorker(OWLOntology ontology, CodeGenerationOptions options,
            CodeGenerationNames names, CodeGenerationInference inference) {
        super(ontology, options, names, inference);
    }

    public static void generateVocabularyCode(OWLOntology ontology, CodeGenerationOptions options,
            CodeGenerationNames names, CodeGenerationInference inference) throws IOException {
        Worker worker = new OnlyVocabularyWorker(ontology, options, names, inference);
        JavaCodeGenerator generator = new JavaCodeGenerator(worker);
        generator.createOnlyVocabulary();
    }

    @Override
    public void initialize() {
        File folder = options.getOutputFolder();
        if (folder != null && !folder.exists()) {
            folder.mkdirs();
        }
        File packageFile;
        String pack = options.getPackage();
        if (pack != null) {
            String packPath = pack.replace('.', '/');
            packageFile = folder == null ? new File(packPath) : new File(folder, packPath);
            packageFile.mkdirs();
        } else {
            packageFile = new File("");
        }
        String subPackage = options.getFactorySubPackage();
        String subPackagePath = subPackage.replace('.', '/');
        File factoryDirectory = new File(packageFile, subPackagePath);
        factoryDirectory.mkdirs();
    }

}
