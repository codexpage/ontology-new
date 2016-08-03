package com.ics.modbus;

import java.util.Arrays;

import com.serotonin.io.serial.SerialParameters;
import com.serotonin.modbus4j.ModbusFactory;
import com.serotonin.modbus4j.ModbusLocator;
import com.serotonin.modbus4j.ModbusMaster;
import com.serotonin.modbus4j.code.DataType;
import com.serotonin.modbus4j.code.RegisterRange;
import com.serotonin.modbus4j.exception.ModbusTransportException;
import com.serotonin.modbus4j.ip.IpParameters;
import com.serotonin.modbus4j.msg.ReadCoilsRequest;
import com.serotonin.modbus4j.msg.ReadCoilsResponse;
import com.serotonin.modbus4j.msg.ReadDiscreteInputsRequest;
import com.serotonin.modbus4j.msg.ReadDiscreteInputsResponse;
import com.serotonin.modbus4j.msg.ReadExceptionStatusRequest;
import com.serotonin.modbus4j.msg.ReadExceptionStatusResponse;
import com.serotonin.modbus4j.msg.ReadHoldingRegistersRequest;
import com.serotonin.modbus4j.msg.ReadHoldingRegistersResponse;
import com.serotonin.modbus4j.msg.ReadInputRegistersRequest;
import com.serotonin.modbus4j.msg.ReadInputRegistersResponse;
import com.serotonin.modbus4j.msg.ReportSlaveIdRequest;
import com.serotonin.modbus4j.msg.ReportSlaveIdResponse;
import com.serotonin.modbus4j.msg.WriteCoilRequest;
import com.serotonin.modbus4j.msg.WriteCoilResponse;
import com.serotonin.modbus4j.msg.WriteCoilsRequest;
import com.serotonin.modbus4j.msg.WriteCoilsResponse;
import com.serotonin.modbus4j.msg.WriteMaskRegisterRequest;
import com.serotonin.modbus4j.msg.WriteMaskRegisterResponse;
import com.serotonin.modbus4j.msg.WriteRegisterRequest;
import com.serotonin.modbus4j.msg.WriteRegisterResponse;
import com.serotonin.modbus4j.msg.WriteRegistersRequest;
import com.serotonin.modbus4j.msg.WriteRegistersResponse;

public class ModbusUtil {
   /*
	public static void main(String[] args) throws Exception {
        SerialParameters serialParameters = new SerialParameters();
        serialParameters.setCommPortId("COM1");
        serialParameters.setPortOwnerName("Numb nuts");
        serialParameters.setBaudRate(9600);

        IpParameters ipParameters = new IpParameters();
        ipParameters.setHost("192.168.1.200");
        ipParameters.setPort(502);
        ModbusFactory modbusFactory = new ModbusFactory();

        // ModbusMaster master = modbusFactory.createRtuMaster(serialParameters, false);
        // ModbusMaster master = modbusFactory.createAsciiMaster(serialParameters);
        ModbusMaster master = modbusFactory.createTcpMaster(ipParameters, false);
        // ModbusMaster master = modbusFactory.createUdpMaster(ipParameters);

        try {
            master.init();
            int slaveId = 1;

            //readCoilTest(master, slaveId, 0,8 );
            // readCoilTest(master, slaveId, 99, 200);
            // readDiscreteInputTest(master, slaveId, 100, 2);
            // readDiscreteInputTest(master, slaveId, 449, 72);
            // readHoldingRegistersTest(master, slaveId, 9, 125);
            // readHoldingRegistersTest(master, slaveId, 9, 120);
            // readInputRegistersTest(master, slaveId, 0, 1);
            // readInputRegistersTest(master, slaveId, 14, 8);
            // writeCoilTest(master, slaveId, 2, false);
            // writeCoilTest(master, slaveId, 110, true);
            // writeRegisterTest(master, slaveId, 1, 1);
            // writeRegisterTest(master, slaveId, 14, 12345);
            // readExceptionStatusTest(master, slaveId);
            // reportSlaveIdTest(master, slaveId);
            // writeCoilsTest(master, slaveId, 0, new boolean[] {false,false,true,false,true,true,true,false,false,true});
            // writeCoilsTest(master, slaveId, 115, new boolean[] {true, false, false, true, false});
            // writeRegistersTest(master, slaveId, 300, new short[] {1, 10, 100, 1000, 10000, (short)65535});
            // writeRegistersTest(master, slaveId, 21, new short[] {1, 10, 100, 1000, 10000, (short)65535});
            // writeMaskRegisterTest(master, slaveId, 26, 0xf2, 0x25);

            // readCoilTest(master, slaveId, 9, 5);
            // readCoilTest(master, slaveId, 10, 5);
            // readDiscreteInputTest(master, slaveId, 10, 6);
            // readDiscreteInputTest(master, slaveId, 10, 5);
            // readHoldingRegistersTest(master, slaveId, 9, 7);
            // readHoldingRegistersTest(master, slaveId, 10, 5);
            // readInputRegistersTest(master, slaveId, 0, 1);
            // readInputRegistersTest(master, slaveId, 10, 5);
            // writeCoilTest(master, slaveId, 8, true);
            // writeCoilTest(master, slaveId, 11, true);
            // writeRegisterTest(master, slaveId, 1, 1);
            // writeRegisterTest(master, slaveId, 14, 12345);
            // readExceptionStatusTest(master, slaveId);
            // reportSlaveIdTest(master, slaveId);
            // writeCoilsTest(master, slaveId, 11, new boolean[] {false, true, false, false, true});
            // writeCoilsTest(master, slaveId, 10, new boolean[] {false, true, false, false, true});
            // writeRegistersTest(master, slaveId, 11, new short[] {(short)65535, 1000, 100, 10, 1});
            // writeRegistersTest(master, slaveId, 10, new short[] {(short)65535, 1000, 100, 10, 1});
            // writeMaskRegisterTest(master, slaveId, 9, 0xf2, 0x25);
            // writeMaskRegisterTest(master, slaveId, 10, 0xf2, 0x25);

            // Automatic WriteMaskRegister failover test
            // ModbusLocator locator = new ModbusLocator(slaveId, RegisterRange.HOLDING_REGISTER, 15, (byte)2);
            // System.out.println(master.getValue(locator));
            // master.setValue(locator, true);
            // System.out.println(master.getValue(locator));
            // master.setValue(locator, false);
            // System.out.println(master.getValue(locator));

            // BatchRead<String> batch = new BatchRead<String>();
            // batch.addLocator("hr1", new ModbusLocator(31, RegisterRange.HOLDING_REGISTER, 80,
            // DataType.TWO_BYTE_BCD));
            // batch.addLocator("hr2", new ModbusLocator(31, RegisterRange.HOLDING_REGISTER, 81,
            // DataType.FOUR_BYTE_BCD));
            // BatchResults<String> results = master.send(batch);
            // System.out.println(results.getValue("hr1"));
            // System.out.println(results.getValue("hr2"));

            // ModbusLocator locator = new ModbusLocator(slaveId, RegisterRange.HOLDING_REGISTER, 50,
            //         DataType.EIGHT_BYTE_INT_UNSIGNED);
            // master.setValue(locator, 10000000);
            // System.out.println(master.getValue(locator));
        }
        finally {
            master.destroy();
        }
    }
*/

	
	public static boolean[] readCoil(ModbusMaster master, int slaveId, int start, int len) {
        
		ReadCoilsRequest request =null;
		ReadCoilsResponse response =null;
		try {
            request = new ReadCoilsRequest(slaveId, start, len);
            response = (ReadCoilsResponse) master.send(request);

            if (response.isException())
                System.out.println("Exception response: message=" + response.getExceptionMessage());
            else
                System.out.println(Arrays.toString(response.getBooleanData()));
        }
        catch (ModbusTransportException e) {
            e.printStackTrace();
        }
		return response.getBooleanData();
    }

    public static void readDiscreteInput(ModbusMaster master, int slaveId, int start, int len) {
        try {
            ReadDiscreteInputsRequest request = new ReadDiscreteInputsRequest(slaveId, start, len);
            ReadDiscreteInputsResponse response = (ReadDiscreteInputsResponse) master.send(request);

            if (response.isException())
                System.out.println("Exception response: message=" + response.getExceptionMessage());
            else
                System.out.println(Arrays.toString(response.getBooleanData()));
        }
        catch (ModbusTransportException e) {
            e.printStackTrace();
        }
    }

    public static void readHoldingRegisters(ModbusMaster master, int slaveId, int start, int len) {
        try {
            ReadHoldingRegistersRequest request = new ReadHoldingRegistersRequest(slaveId, start, len);
            ReadHoldingRegistersResponse response = (ReadHoldingRegistersResponse) master.send(request);

            if (response.isException())
                System.out.println("Exception response: message=" + response.getExceptionMessage());
            else
                System.out.println(Arrays.toString(response.getShortData()));
        }
        catch (ModbusTransportException e) {
            e.printStackTrace();
        }
    }

    public static void readInputRegisters(ModbusMaster master, int slaveId, int start, int len) {
        try {
            ReadInputRegistersRequest request = new ReadInputRegistersRequest(slaveId, start, len);
            ReadInputRegistersResponse response = (ReadInputRegistersResponse) master.send(request);

            if (response.isException())
                System.out.println("Exception response: message=" + response.getExceptionMessage());
            else
                System.out.println(Arrays.toString(response.getShortData()));
        }
        catch (ModbusTransportException e) {
            e.printStackTrace();
        }
    }

    public static void writeCoil(ModbusMaster master, int slaveId, int offset, boolean value) {
        try {
            WriteCoilRequest request = new WriteCoilRequest(slaveId, offset, value);
            WriteCoilResponse response = (WriteCoilResponse) master.send(request);

            if (response.isException())
                System.out.println("Exception response: message=" + response.getExceptionMessage());
            else
                System.out.println("Success");
        }
        catch (ModbusTransportException e) {
            e.printStackTrace();
        }
    }

    public static void writeRegister(ModbusMaster master, int slaveId, int offset, int value) {
        try {
            WriteRegisterRequest request = new WriteRegisterRequest(slaveId, offset, value);
            WriteRegisterResponse response = (WriteRegisterResponse) master.send(request);

            if (response.isException())
                System.out.println("Exception response: message=" + response.getExceptionMessage());
            else
                System.out.println("Success");
        }
        catch (ModbusTransportException e) {
            e.printStackTrace();
        }
    }

    public static void readExceptionStatus(ModbusMaster master, int slaveId) {
        try {
            ReadExceptionStatusRequest request = new ReadExceptionStatusRequest(slaveId);
            ReadExceptionStatusResponse response = (ReadExceptionStatusResponse) master.send(request);

            if (response.isException())
                System.out.println("Exception response: message=" + response.getExceptionMessage());
            else
                System.out.println(response.getExceptionStatus());
        }
        catch (ModbusTransportException e) {
            e.printStackTrace();
        }
    }

    public static void reportSlaveId(ModbusMaster master, int slaveId) {
        try {
            ReportSlaveIdRequest request = new ReportSlaveIdRequest(slaveId);
            ReportSlaveIdResponse response = (ReportSlaveIdResponse) master.send(request);

            if (response.isException())
                System.out.println("Exception response: message=" + response.getExceptionMessage());
            else
                System.out.println(Arrays.toString(response.getData()));
        }
        catch (ModbusTransportException e) {
            e.printStackTrace();
        }
    }

    public static void writeCoils(ModbusMaster master, int slaveId, int start, boolean[] values) {
        try {
            WriteCoilsRequest request = new WriteCoilsRequest(slaveId, start, values);
            WriteCoilsResponse response = (WriteCoilsResponse) master.send(request);

            if (response.isException())
                System.out.println("Exception response: message=" + response.getExceptionMessage());
            else
                System.out.println("Success");
        }
        catch (ModbusTransportException e) {
            e.printStackTrace();
        }
    }

    public static void writeRegisters(ModbusMaster master, int slaveId, int start, short[] values) {
        try {
            WriteRegistersRequest request = new WriteRegistersRequest(slaveId, start, values);
            WriteRegistersResponse response = (WriteRegistersResponse) master.send(request);

            if (response.isException())
                System.out.println("Exception response: message=" + response.getExceptionMessage());
            else
                System.out.println("Success");
        }
        catch (ModbusTransportException e) {
            e.printStackTrace();
        }
    }

    public static void writeMaskRegister(ModbusMaster master, int slaveId, int offset, int and, int or) {
        try {
            WriteMaskRegisterRequest request = new WriteMaskRegisterRequest(slaveId, offset, and, or);
            WriteMaskRegisterResponse response = (WriteMaskRegisterResponse) master.send(request);

            if (response.isException())
                System.out.println("Exception response: message=" + response.getExceptionMessage());
            else
                System.out.println("Success");
        }
        catch (ModbusTransportException e) {
            e.printStackTrace();
        }
    }
}
