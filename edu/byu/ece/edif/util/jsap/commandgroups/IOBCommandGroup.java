/*
 * TODO: Insert class description here.
 * 
 * Copyright (c) 2008 Brigham Young University
 * 
 * This file is part of the BYU EDIF Tools.
 * 
 * BYU EDIF Tools is free software: you may redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 2 of the License, or (at your option) any
 * later version.
 * 
 * BYU EDIF Tools is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * A copy of the GNU General Public License is included with the BYU EDIF Tools.
 * It can be found at /edu/byu/edif/doc/gpl2.txt. You may also get a copy of the
 * license at <http://www.gnu.org/licenses/>.
 * 
 */
package edu.byu.ece.edif.util.jsap.commandgroups;

import com.martiansoftware.jsap.FlaggedOption;
import com.martiansoftware.jsap.JSAP;
import com.martiansoftware.jsap.JSAPResult;
import com.martiansoftware.jsap.Switch;
import com.martiansoftware.jsap.stringparsers.EnumeratedStringParser;


public class IOBCommandGroup extends AbstractCommandGroup {

    public IOBCommandGroup() {

        FlaggedOption pack_iob_registers = new FlaggedOption(PACK_IOB_REGISTERS);
        pack_iob_registers.setStringParser(EnumeratedStringParser.getParser(INPUT + ";" + OUTPUT + ";" + BOTH
                + ";" + NONE, false));
        pack_iob_registers.setLongFlag(PACK_IOB_REGISTERS);
        pack_iob_registers.setDefault(BOTH);
        pack_iob_registers.setRequired(false);
        pack_iob_registers.setUsageName("{" + INPUT + "|" + OUTPUT + "|" + BOTH + "|" + NONE + "}");
        pack_iob_registers.setHelp("IOB analysis will by default treat registers that can be packed"
                + " into the IOB differently. '" + INPUT + "' will pack input registers into IOBs, '" + OUTPUT
                + "' will pack output registers, '" + BOTH + "' will pack both. '" + NONE + "' will pack neither.");
        this.addCommand(pack_iob_registers);
        


    }

    public static boolean packInputRegisters(JSAPResult result) {
        String packRegisters = result.getString(PACK_IOB_REGISTERS);
        if (packRegisters != null) {
            if (packRegisters.equalsIgnoreCase(INPUT) || packRegisters.equalsIgnoreCase(BOTH))
                return true;
            else
                return false;
        }
        return true;
    }

    public static boolean packOutputRegisters(JSAPResult result) {
        String packRegisters = result.getString(PACK_IOB_REGISTERS);
        if (packRegisters != null) {
            if (packRegisters.equalsIgnoreCase(OUTPUT) || packRegisters.equalsIgnoreCase(BOTH))
                return true;
            else
                return false;
        }
        return true;
    }
    


    public static final String PACK_IOB_REGISTERS = "pack_registers";

    public static final String INPUT = "i";

    public static final String OUTPUT = "o";

    public static final String BOTH = "b";

    public static final String NONE = "n";

    public static final String FALSE = "false";

    public static final String TRUE = "true";

}