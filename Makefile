JC=javac
OBLIG_1=thomaswaaler/oblig1
OBLIG_2=thomaswaaler/oblig2
OBLIG_3=thomaswaaler/oblig3/SortingMethods
OBLIG_4=thomaswaaler/oblig4/TekstAnalyse
OBLIG_5=thomaswaaler/oblig5/Hashing

obligs: $(OBLIG_1)/Flyplassen.class $(OBLIG_2)/SpringerProblemet.class $(OBLIG_3).class $(OBLIG_4).class $(OBLIG_5).class

$(OBLIG_1)/Flyplassen.class: $(OBLIG_1)/Flyplassen.java
	$(JC) $(OBLIG_1)/Flyplassen.java

$(OBLIG_2)/SpringerProblemet.class: $(OBLIG_2)/SpringerProblemet.java
	$(JC) $(OBLIG_2)/SpringerProblemet.java

$(OBLIG_3).class: $(OBLIG_3).java
	$(JC) $(OBLIG_3).java

$(OBLIG_4).class: $(OBLIG_4).java
	$(JC) $(OBLIG_4).java

$(OBLIG_5).class: $(OBLIG_5).java
	$(JC) $(OBLIG_5).java
