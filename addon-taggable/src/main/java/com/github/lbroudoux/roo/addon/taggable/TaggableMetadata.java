package com.github.lbroudoux.roo.addon.taggable;

import static org.springframework.roo.model.JpaJavaType.ELEMENT_COLLECTION;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.roo.classpath.PhysicalTypeIdentifierNamingUtils;
import org.springframework.roo.classpath.PhysicalTypeMetadata;
import org.springframework.roo.classpath.details.FieldMetadata;
import org.springframework.roo.classpath.details.FieldMetadataBuilder;
import org.springframework.roo.classpath.details.MethodMetadata;
import org.springframework.roo.classpath.details.MethodMetadataBuilder;
import org.springframework.roo.classpath.details.annotations.AnnotatedJavaType;
import org.springframework.roo.classpath.details.annotations.AnnotationMetadataBuilder;
import org.springframework.roo.classpath.itd.AbstractItdTypeDetailsProvidingMetadataItem;
import org.springframework.roo.classpath.itd.InvocableMemberBodyBuilder;
import org.springframework.roo.metadata.MetadataIdentificationUtils;
import org.springframework.roo.model.DataType;
import org.springframework.roo.model.JavaSymbolName;
import org.springframework.roo.model.JavaType;
import org.springframework.roo.project.LogicalPath;

/**
 * This type produces metadata for a new ITD. It uses an {@link ItdTypeDetailsBuilder} provided by 
 * {@link AbstractItdTypeDetailsProvidingMetadataItem} to register a field in the ITD and a new method.
 * 
 * @since 1.1.0
 */
public class TaggableMetadata extends AbstractItdTypeDetailsProvidingMetadataItem {

   // Constants
	private static final String PROVIDES_TYPE_STRING = TaggableMetadata.class.getName();
	private static final String PROVIDES_TYPE = MetadataIdentificationUtils.create(PROVIDES_TYPE_STRING);

	private String entityName;
	private JavaType tagsType = new JavaType(java.util.Set.class.getName(), 0, DataType.TYPE, null,
         Arrays.asList(JavaType.STRING));
	
   public static final String getMetadataIdentiferType() {
      return PROVIDES_TYPE;
   }
    
   public static final String createIdentifier(JavaType javaType, LogicalPath path) {
      return PhysicalTypeIdentifierNamingUtils.createIdentifier(PROVIDES_TYPE_STRING, javaType, path);
   }

   public static final JavaType getJavaType(String metadataIdentificationString) {
      return PhysicalTypeIdentifierNamingUtils.getJavaType(PROVIDES_TYPE_STRING, metadataIdentificationString);
   }

   public static final LogicalPath getPath(String metadataIdentificationString) {
      return PhysicalTypeIdentifierNamingUtils.getPath(PROVIDES_TYPE_STRING, metadataIdentificationString);
   }

   public static boolean isValid(String metadataIdentificationString) {
      return PhysicalTypeIdentifierNamingUtils.isValid(PROVIDES_TYPE_STRING, metadataIdentificationString);
   }
    
	public TaggableMetadata(String identifier, JavaType aspectName, PhysicalTypeMetadata governorPhysicalTypeMetadata) {
		super(identifier, aspectName, governorPhysicalTypeMetadata);
		Validate.isTrue(isValid(identifier), "Metadata identification string '" + identifier + "' does not appear to be a valid");

		// Initialize entity name.
		entityName = getJavaType(identifier).getSimpleTypeName();
		
		// Add tags fields and methods.
		builder.addField(getTagsField());
		builder.addMethod(getTagsAccessor());
		builder.addMethod(getTagAddMethod());
		builder.addMethod(getTagFinderMethod());
		
		// Create a representation of the desired output ITD
		itdTypeDetails = builder.build();
	}
	
	/**
	 * Create metadata for tags field definition. 
	 * @return a FieldMetadata object
	 */
	private FieldMetadata getTagsField() {
      List<AnnotationMetadataBuilder> annotations = new ArrayList<AnnotationMetadataBuilder>();
      annotations.add(new AnnotationMetadataBuilder(ELEMENT_COLLECTION));
	   
		// Using the FieldMetadataBuilder to create the field definition. 
		FieldMetadataBuilder fieldBuilder = new FieldMetadataBuilder(getId(), // Metadata ID provided by supertype
			Modifier.PRIVATE, 
			annotations, // The annotations for this field
			new JavaSymbolName("tags"), // Field name
			tagsType); // Field type
		
		fieldBuilder.setFieldInitializer("new java.util.HashSet<String>()");
		
		return fieldBuilder.build(); // Build and return a FieldMetadata instance
	}
	
	private MethodMetadata getTagsAccessor() {
		// Specify the desired method name
		JavaSymbolName methodName = new JavaSymbolName("getTags");
		
		// Check if a method with the same signature already exists in the target type
		MethodMetadata method = methodExists(methodName, new ArrayList<AnnotatedJavaType>());
		if (method != null) {
			return method;
		}
		
		// Define method parameter types (none in this case)
		List<AnnotatedJavaType> parameterTypes = new ArrayList<AnnotatedJavaType>();
		
		// Define method parameter names (none in this case)
		List<JavaSymbolName> parameterNames = new ArrayList<JavaSymbolName>();
		
		// Create the method body
		InvocableMemberBodyBuilder bodyBuilder = new InvocableMemberBodyBuilder();
		bodyBuilder.appendFormalLine("return this.tags;");
		
		// Use the MethodMetadataBuilder for easy creation of MethodMetadata
		MethodMetadataBuilder methodBuilder = new MethodMetadataBuilder(getId(), Modifier.PUBLIC, methodName, 
		      tagsType, parameterTypes, parameterNames, bodyBuilder);
		
		return methodBuilder.build(); // Build and return a MethodMetadata instance
	}
	
	private MethodMetadata getTagAddMethod() {
	   // Specify the desired method name
      JavaSymbolName methodName = new JavaSymbolName("addTag");
      
      // Check if a method with the same signature already exists in the target type
      MethodMetadata method = methodExists(methodName, new ArrayList<AnnotatedJavaType>());
      if (method != null) {
         return method;
      }
      
      // Define method parameter types
      List<JavaType> parameterTypes = Arrays.asList(JavaType.STRING);
      
      // Define method parameter names
      List<JavaSymbolName> parameterNames = Arrays.asList(new JavaSymbolName("tag"));
      
      // Create the method body
      InvocableMemberBodyBuilder bodyBuilder = new InvocableMemberBodyBuilder();
      bodyBuilder.appendFormalLine("this.tags.add(tag);");
      
      // Use the MethodMetadataBuilder for easy creation of MethodMetadata
      MethodMetadataBuilder methodBuilder = new MethodMetadataBuilder(getId(), Modifier.PUBLIC, methodName, 
            JavaType.VOID_PRIMITIVE, AnnotatedJavaType.convertFromJavaTypes(parameterTypes), 
            parameterNames, bodyBuilder);
      
	   return methodBuilder.build();
	}
	
	private MethodMetadata getTagFinderMethod() {
      // Specify the desired method name
      JavaSymbolName methodName = new JavaSymbolName("findAll" + entityName + "sWithTag");
      
      // Check if a method with the same signature already exists in the target type
      MethodMetadata method = methodExists(methodName, new ArrayList<AnnotatedJavaType>());
      if (method != null) {
         // If it already exists, just return the method and omit its generation via the ITD
         return method;
      }
      
      // Define method parameter types
      List<JavaType> parameterTypes = Arrays.asList(JavaType.STRING);
      
      // Define method parameter names
      List<JavaSymbolName> parameterNames = Arrays.asList(new JavaSymbolName("tag"));

      // Define return type.
      JavaType returnType = JavaType.listOf(destination);
      
      // Create the method body
      InvocableMemberBodyBuilder bodyBuilder = new InvocableMemberBodyBuilder();
      bodyBuilder.appendFormalLine("return entityManager().createQuery(\"SELECT o FROM " 
            + entityName + " o WHERE '\" + tag + \"' IN ELEMENTS(o.tags)\", " + entityName + ".class).getResultList();");
      
      // Use the MethodMetadataBuilder for easy creation of MethodMetadata
      MethodMetadataBuilder methodBuilder = new MethodMetadataBuilder(getId(), Modifier.PUBLIC | Modifier.STATIC, methodName, 
            returnType, AnnotatedJavaType.convertFromJavaTypes(parameterTypes), 
            parameterNames, bodyBuilder);
      
      return methodBuilder.build();
   }
		
	private MethodMetadata methodExists(JavaSymbolName methodName, List<AnnotatedJavaType> paramTypes) {
		// We have no access to method parameter information, so we scan by name alone and treat any match as authoritative
		// We do not scan the superclass, as the caller is expected to know we'll only scan the current class
		for (MethodMetadata method : governorTypeDetails.getDeclaredMethods()) {
			if (method.getMethodName().equals(methodName) && method.getParameterTypes().equals(paramTypes)) {
				// Found a method of the expected name; we won't check method parameters though
				return method;
			}
		}
		return null;
	}
	
	// Typically, no changes are required beyond this point
	
	public String toString() {
		ToStringBuilder tsb = new ToStringBuilder(this);
		tsb.append("identifier", getId());
		tsb.append("valid", valid);
		tsb.append("aspectName", aspectName);
		tsb.append("destinationType", destination);
		tsb.append("governor", governorPhysicalTypeMetadata.getId());
		tsb.append("itdTypeDetails", itdTypeDetails);
		return tsb.toString();
	}
}
