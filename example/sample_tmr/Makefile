
DESIGN=dut_processor_top
INPUT_PATH=../src

BLTMR_PATH=/home/karel/FIT/Workspace/BL-TMR_Tool/byuediftools
JSAP_JAR=../../java_libs/JSAP-2.1.jar
JHDL_JAR=../../java_libs/JHDL-provisional.jar

JVM_OPTS = -Xmx8G --class-path $(BLTMR_PATH):$(JSAP_JAR):$(JHDL_JAR)

PART = xc5vsx50t-ff1136-1

BUILD = edu.byu.ece.edif.jedif.JEdifBuild
ANALYZE = edu.byu.ece.edif.jedif.JEdifAnalyze
NMR_SELECTION = edu.byu.ece.edif.jedif.JEdifNMRSelection
VOTER_SELECTION = edu.byu.ece.edif.jedif.JEdifVoterSelection
MFV = edu.byu.ece.edif.jedif.JEdifMoreFrequentVoting
DETECTION_SELECTION = edu.byu.ece.edif.jedif.JEdifDetectionSelection
PERSISTENCE_DETECTION = edu.byu.ece.edif.jedif.JEdifPersistenceDetection
NMR = edu.byu.ece.edif.jedif.JEdifNMR


# BUILD_OPTS:
# --remove_hl    - Remove half-latches in the input design before performing replication.
# --remove_fmaps - Remove FMAPs from the input design.
# --remove_rlocs - Remove ALL RLOCs in the design. The replication tools will not 
#                  work correctly if a design contains RLOCs.
# --replace_luts - the tool replaces LUT RAMs and SRLs with flip-flop equivalents. 
#                  This optionis useful because bitstream scrubbing cannot be used 
#                  with designs that contain LUT RAMs or SRLs
BUILD_OPTS = --remove_hl \
	     --remove_fmaps \
	     --remove_rlocs \
	     --no_open_pins \
	     --no_delete_cells \
	     #--replace_luts \

ANALYZE_OPTS = --part $(PART) \
	       --no_iob_feedback \

#NMR_SELECTION_OPTS = --part $(PART) --replication_type triplication --full_nmr --nmr_inports --nmr_outports
NMR_SELECTION_OPTS = --part $(PART) \
		     --replication_type triplication \
		     --full_nmr 

#		     --no_nmr_feedback \
#		     --no_nmr_input_to_feedback \
#		     --no_nmr_feedback_output \
#		     --no_nmr_feed_forward \

VOTER_OPTS =  --basic_decomposition

NMR_OPTS = --part $(PART) --edif --verbose 5

all: $(DESIGN)_nmr.edf

$(DESIGN)_nmr.edf: $(DESIGN).jedif voter_selection.touch
	@echo "--> JEdifNMR"
	java $(JVM_OPTS) $(NMR) $(DESIGN).jedif --rep_desc $(DESIGN).rdesc $(NMR_OPTS) -o \
	    $(DESIGN)_nmr.edf

voter_selection.touch: $(DESIGN).jedif nmr_selection.touch
	@echo "--> Run JEdifVoterSelection"
	java $(JVM_OPTS) $(VOTER_SELECTION) $(DESIGN).jedif --rep_desc $(DESIGN).rdesc \
	    --c_desc $(DESIGN).cdesc $(VOTER_OPTS)
	touch voter_selection.touch

nmr_selection.touch: $(DESIGN).cdesc
	@echo "--> Run JEdifNMRSelection"
	java $(JVM_OPTS) $(NMR_SELECTION) $(DESIGN).jedif --c_desc $(DESIGN).cdesc \
	    --rep_desc $(DESIGN).rdesc $(NMR_SELECTION_OPTS)
	touch nmr_selection.touch

$(DESIGN).cdesc: $(DESIGN).jedif
	@echo "--> Run JEdifAnalyze"
	java $(JVM_OPTS) $(ANALYZE) $(DESIGN).jedif -o $(DESIGN).cdesc $(ANALYZE_OPTS)

$(DESIGN).jedif: $(DESIGN).edf
	@echo "--> Convert $(DESIGN).edf to .JEDIF"
	java $(JVM_OPTS) $(BUILD) $(DESIGN).edf $(BUILD_OPTS)

$(DESIGN).edf: $(INPUT_PATH)/$(DESIGN).ngc
	ngc2edif $(INPUT_PATH)/$(DESIGN).ngc $(DESIGN).edf

clean:
	rm -rf $(DESIGN).edf $(DESIGN).jedif $(DESIGN).cdesc $(DESIGN).rdesc $(DESIGN)_nmr.edf *.log *.touch _xmsgs


