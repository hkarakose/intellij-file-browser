/*
  Copyright (c) 2007, Robert F. Beeger (robert at beeger dot net)
  All rights reserved.

  Redistribution and use in source and binary forms, with or without modification,
  are permitted provided that the following conditions are met:

      * Redistributions of source code must retain the above copyright notice, this list
        of conditions and the following disclaimer.
      * Redistributions in binary form must reproduce the above copyright notice, this
        list of conditions and the following disclaimer in the documentation and/or other
        materials provided with the distribution.
      * Neither the name of 'Robert F. Beeger' nor the names of his contributors may be
        used to endorse or promote products derived from this software without specific
        prior written permission.

  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY
  EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
  MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL
  THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
  SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT
  OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
  HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
  TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
  EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package net.beeger.filebrowser;

import com.intellij.openapi.vfs.VirtualFile;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class FileTransferable implements Transferable
{
  public FileTransferable(VirtualFile file)
  {
    _file = file;
  }

  public DataFlavor[] getTransferDataFlavors()
  {
    return SUPPORTED_DATAFLAVORS;
  }

  public boolean isDataFlavorSupported(DataFlavor flavor)
  {
    return SUPPORTED_DATAFLAVORS_LIST.contains(flavor);
  }

  public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException
  {
    if (DataFlavor.javaFileListFlavor.equals(flavor))
    {
      ArrayList<File> files = new ArrayList<File>();
      files.add(new File(_file.getPath()));
      return files;
    }
    else if (DataFlavor.stringFlavor.equals(flavor))
    {
      return new File(_file.getPath()).getAbsolutePath();
    }
    else
    {
      throw new UnsupportedFlavorException(flavor);
    }
  }

  private static final DataFlavor[] SUPPORTED_DATAFLAVORS = new DataFlavor[]{DataFlavor.javaFileListFlavor, DataFlavor.stringFlavor};
  private static final List<DataFlavor> SUPPORTED_DATAFLAVORS_LIST = Arrays.asList(SUPPORTED_DATAFLAVORS);
  private final VirtualFile _file;
}
