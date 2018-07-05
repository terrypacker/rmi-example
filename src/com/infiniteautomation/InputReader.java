/**
 * Copyright (C) 2018 Infinite Automation Software. All rights reserved.
 */
package com.infiniteautomation;

import com.serotonin.io.StreamUtils;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.StringWriter;

/**
 *
 * @author Terry Packer
 */
public class InputReader implements Serializable {
    private static final long serialVersionUID = 1L;
    private final InputStreamReader reader;
    private final StringWriter writer = new StringWriter();

    InputReader(InputStream is) {
        reader = new InputStreamReader(is);
    }

    public void join() throws IOException {
        StreamUtils.transfer(reader, writer);
    }

    public String getInput() {
        return writer.toString();
    }
}
