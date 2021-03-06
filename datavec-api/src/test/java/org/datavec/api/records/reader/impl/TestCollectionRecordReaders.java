/*
 *  * Copyright 2016 Skymind, Inc.
 *  *
 *  *    Licensed under the Apache License, Version 2.0 (the "License");
 *  *    you may not use this file except in compliance with the License.
 *  *    You may obtain a copy of the License at
 *  *
 *  *        http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  *    Unless required by applicable law or agreed to in writing, software
 *  *    distributed under the License is distributed on an "AS IS" BASIS,
 *  *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  *    See the License for the specific language governing permissions and
 *  *    limitations under the License.
 */

package org.datavec.api.records.reader.impl;

import org.datavec.api.records.SequenceRecord;
import org.datavec.api.records.metadata.RecordMetaData;
import org.datavec.api.records.reader.impl.collection.CollectionSequenceRecordReader;
import org.datavec.api.writable.IntWritable;
import org.datavec.api.records.reader.SequenceRecordReader;
import org.datavec.api.writable.Writable;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Alex on 21/05/2016.
 */
public class TestCollectionRecordReaders {

    @Test
    public void testCollectionSequenceRecordReader() throws Exception {

        List<List<List<Writable>>> listOfSequences = new ArrayList<>();

        List<List<Writable>> sequence1 = new ArrayList<>();
        sequence1.add(Arrays.asList((Writable)new IntWritable(0), new IntWritable(1)));
        sequence1.add(Arrays.asList((Writable)new IntWritable(2), new IntWritable(3)));
        listOfSequences.add(sequence1);

        List<List<Writable>> sequence2 = new ArrayList<>();
        sequence2.add(Arrays.asList((Writable)new IntWritable(4), new IntWritable(5)));
        sequence2.add(Arrays.asList((Writable)new IntWritable(6), new IntWritable(7)));
        listOfSequences.add(sequence2);

        SequenceRecordReader seqRR = new CollectionSequenceRecordReader(listOfSequences);
        assertTrue(seqRR.hasNext());

        assertEquals(sequence1, seqRR.sequenceRecord());
        assertEquals(sequence2, seqRR.sequenceRecord());
        assertFalse(seqRR.hasNext());

        seqRR.reset();
        assertEquals(sequence1, seqRR.sequenceRecord());
        assertEquals(sequence2, seqRR.sequenceRecord());
        assertFalse(seqRR.hasNext());

        //Test metadata:
        seqRR.reset();
        List<List<List<Writable>>> out2 = new ArrayList<>();
        List<SequenceRecord> seq = new ArrayList<>();
        List<RecordMetaData> meta = new ArrayList<>();

        while(seqRR.hasNext()){
            SequenceRecord r = seqRR.nextSequence();
            out2.add(r.getSequenceRecord());
            seq.add(r);
            meta.add(r.getMetaData());
        }

        assertEquals(listOfSequences, out2);

        List<SequenceRecord> fromMeta = seqRR.loadSequenceFromMetaData(meta);
        assertEquals(seq, fromMeta);
    }

}
