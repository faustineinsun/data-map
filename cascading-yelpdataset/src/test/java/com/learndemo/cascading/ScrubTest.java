package com.learndemo.cascading;

import static org.junit.Assert.*;

import org.junit.Test;

import com.learndemo.cascading.ScrubFunction;

import cascading.tuple.Fields;

public class ScrubTest
  {
  @Test
  public void testMain() throws Exception
    {
    @SuppressWarnings("unused")
    ScrubTest tester = new ScrubTest();
    Fields fieldDeclaration = new Fields( "doc_id", "token" );
    ScrubFunction scrub = new ScrubFunction( fieldDeclaration );
    assertEquals( "Scrub", "foo bar", scrub.scrubText( "FoO BAR  " ) );
    }
  }