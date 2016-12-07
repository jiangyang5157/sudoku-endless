package com.gmail.jiangyang5157.sudoku;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.gmail.jiangyang5157.sudoku.puzzle.NodeCache;
import com.gmail.jiangyang5157.sudoku.puzzle.PuzzleCache;
import com.gmail.jiangyang5157.sudoku.puzzle.render.node.Node;
import com.gmail.jiangyang5157.sudoku.sql.AppDatabaseApi;
import com.gmail.jiangyang5157.sudoku.sql.PuzzleTable;
import com.gmail.jiangyang5157.tookit.android.base.AppUtils;
import com.gmail.jiangyang5157.tookit.android.base.EncodeUtils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * read & write xml file
 *
 * @author JiangYang
 */
public class XmlUtils {
    private static final String TAG = "[XmlUtils]";

    public static File write(Context context, String path, HashSet<Long> rowIds) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = null;
        try {
            db = dbf.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        assert db != null;
        Document doc = db.newDocument();
        doc.setXmlStandalone(true);

        String rootName = AppUtils.getString(context, R.string.app_name).replaceAll(" ", "");
        Element root = doc.createElement(rootName);
        for (Long rowId1 : rowIds) {
            String rowId = String.valueOf(rowId1);
            Cursor cursor = AppDatabaseApi.getInstance(context).queryPuzzle(PuzzleTable.KEY_ROWID, rowId, PuzzleTable.KEY_DATE + " " + AppDatabaseApi.ORDER_BY_DESC);

            String srcCache = cursor.getString(cursor.getColumnIndexOrThrow(PuzzleTable.KEY_CACHE));
            String best_time = cursor.getString(cursor.getColumnIndexOrThrow(PuzzleTable.KEY_BEST_TIME));

            String dstCache = "";
            try {
                PuzzleCache puzzleCache = (PuzzleCache) EncodeUtils.decodeObject(srcCache);
                NodeCache[][] nodesCache = puzzleCache.getNodesCache();
                for (int i = 0; i < Config.SUDOKU_SIZE; i++) {
                    for (int j = 0; j < Config.SUDOKU_SIZE; j++) {
                        NodeCache nodeCache = nodesCache[i][j];
                        //clear progress
                        nodeCache.setValue(nodeCache.isEditable() ? 0 : nodeCache.getPuzzleValue());

                        ArrayList<Integer> cellsValue = new ArrayList<Integer>();
                        for (int index = 0; index < Node.CELLS_COUNT; index++) {
                            cellsValue.add(0);
                        }
                        nodeCache.setCellsValue(cellsValue);
                    }
                }
                EncodeUtils.encodeObject(puzzleCache);
            } catch (ClassNotFoundException | IOException e) {
                e.printStackTrace();
            }

            Element ePuzzle = doc.createElement(PuzzleTable.TABLE_NAME);
            ePuzzle.setAttribute(PuzzleTable.KEY_CACHE, dstCache);
            ePuzzle.setAttribute(PuzzleTable.KEY_BEST_TIME, best_time);

            root.appendChild(ePuzzle);
        }

        doc.appendChild(root);

        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = null;
        try {
            transformer = tf.newTransformer();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        }
        DOMSource source = new DOMSource(doc);
        assert transformer != null;
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        PrintWriter pw = null;
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(path);
            pw = new PrintWriter(fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        StreamResult result = new StreamResult(pw);
        try {
            transformer.transform(source, result);
        } catch (TransformerException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "Write: " + path);
        try {
            assert fos != null;
            fos.flush();
            fos.close();
            pw.flush();
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new File(path);
    }

    public static boolean read(Context context, String path) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = null;
        try {
            db = dbf.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        File file = new File(path);
        Log.d(TAG, "Read: " + file.getAbsolutePath());
        Document doc = null;
        try {
            assert db != null;
            doc = db.parse(file);
        } catch (SAXException | IOException e) {
            e.printStackTrace();
        }

        String rootName = AppUtils.getString(context, R.string.app_name).replaceAll(" ", "");
        assert doc != null;
        NodeList rootNodes = doc.getElementsByTagName(rootName);
        int rootNodestLength = rootNodes.getLength();
        if (rootNodestLength > 0) {
            Element eRoot = (Element) rootNodes.item(0);

            NodeList eRootNodes = eRoot.getElementsByTagName(PuzzleTable.TABLE_NAME);
            int eRootNodesLength = eRootNodes.getLength();
            for (int i = 0; i < eRootNodesLength; i++) {
                Element ePuzzle = (Element) eRootNodes.item(i);

                String cache = ePuzzle.getAttribute(PuzzleTable.KEY_CACHE);
                String drawable = "";
                String longDate = String.valueOf((new Date()).getTime());
                String timer = "0";
                String best_time = ePuzzle.getAttribute(PuzzleTable.KEY_BEST_TIME);
                long rowId = AppDatabaseApi.getInstance(context).insertPuzzle(cache, drawable, longDate, timer, best_time);
                Log.d(TAG, "Read rowId:" + rowId);
            }
        }

        return true;
    }
}
