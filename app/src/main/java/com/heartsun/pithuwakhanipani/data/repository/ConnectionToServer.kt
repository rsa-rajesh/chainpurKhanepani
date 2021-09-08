package com.heartsun.pithuwakhanipani.data.repository

import com.heartsun.pithuwakhanipani.data.Prefs
import com.heartsun.pithuwakhanipani.domain.dbmodel.*
import com.heartsun.pithuwakhanipani.utils.connectionUtils.SqlServerFunctions
import java.sql.Connection
import java.sql.ResultSet
import java.sql.Statement
import android.content.Context

import android.graphics.BitmapFactory

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidcommon.utils.FilePath

import com.heartsun.pithuwakhanipani.domain.*
import timber.log.Timber
import java.io.*
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*


class ConnectionToServer(prefs: Prefs) {
    var prefs: Prefs = prefs
    fun getRates(): WaterRateListResponse {
        var stmt: Statement? = null
        var resultset: ResultSet? = null
        var resultset2: ResultSet? = null
        var resultset3: ResultSet? = null

        val query = "select * from TBLReadingSetupDtl"
        val query2 = "select * from TBLReadingSetup"
        val query3 = "select * from tblTapTypeMaster"

        val ss = SqlServerFunctions()
        val conn: Connection = ss.ConnectToSQLServer(prefs)
        stmt = conn.createStatement()


        var tapTypeList: MutableList<TblTapTypeMaster> = arrayListOf()
        var readingSetupList: MutableList<TBLReadingSetup> = arrayListOf()
        var readingSetupDetailsList: MutableList<TBLReadingSetupDtl> = arrayListOf()



        resultset = stmt.executeQuery(query)
        while (resultset.next()) {

            val readingSetupDetails: TBLReadingSetupDtl = TBLReadingSetupDtl(
                VNO = resultset.getInt("VNO"),
                SrNo = resultset.getInt("SrNo"),
                MnNo = resultset.getInt("MnNo"),
                MxNo = resultset.getInt("MxNo"),
                Amt = resultset.getFloat("Amt"),
                Rate = resultset.getFloat("Rate")
            )
            readingSetupDetailsList.add(readingSetupDetails)
        }


        resultset2 = stmt.executeQuery(query2)
        while (resultset2.next()) {
            val readingSetup: TBLReadingSetup = TBLReadingSetup(
                VNO = resultset2.getInt("VNO"),
                FixCharges = resultset2.getInt("FixCharges"),
                TapTypeID = resultset2.getInt("TapTypeID"),
                TapSizeID = resultset2.getInt("TapSizeID"),
                Remarks = resultset2.getString("Remarks")
            )
            readingSetupList.add(readingSetup)

        }

        resultset3 = stmt.executeQuery(query3)
        while (resultset3.next()) {
            val tapType: TblTapTypeMaster = TblTapTypeMaster(
                TapTypeID = resultset3.getInt("TapTypeID"),
                TapTypeName = resultset3.getString("TapTypeName")
            )
            tapTypeList.add(tapType)
        }

        conn.close()

        return WaterRateListResponse(
            readingSetup = readingSetupList,
            readingSetupDetails = readingSetupDetailsList,
            tapType = tapTypeList
        )
    }

    fun getMembers(context: Context): MembersListResponse {
        var stmt: Statement? = null
        var resultset: ResultSet? = null
        var resultset2: ResultSet? = null

        val query = "select * from tblBoardMemberType"
        val query2 = "select * from tblContact"

        val ss = SqlServerFunctions()
        val conn: Connection = ss.ConnectToSQLServer(prefs)
        stmt = conn.createStatement()

        var memberTypeList: MutableList<TblBoardMemberType> = arrayListOf()
        var membersList: MutableList<TblContact> = arrayListOf()

        resultset = stmt.executeQuery(query2)
        while (resultset.next()) {

            if (resultset.getBytes("Photo") != null) {
                val data: ByteArray = resultset.getBytes("Photo")
                val imageStream = ByteArrayInputStream(data)
                val theImage = BitmapFactory.decodeStream(imageStream)
                val image = File(
                    context.getExternalFilesDir(null),
                    "memberImage" + resultset.getInt("ContID").toString() + ".png"
                )
                val fos = FileOutputStream(image)
                fos.use { theImage.compress(Bitmap.CompressFormat.PNG, 100, it) }
                val tblContact: TblContact = TblContact(
                    ContID = resultset.getInt("ContID"),
                    ContactName = resultset.getString("ContactName"),
                    ContactNumber = resultset.getString("ContactNumber"),
                    IsActive = resultset.getInt("IsActive"),
                    Post = resultset.getString("Post"),
                    MemberType = resultset.getInt("MemberType"),
                    Tenure = resultset.getString("Tenure"),
                    Address = resultset.getString("Address"),
                    Image = Uri.parse(image.path).toString()
                )
                membersList.add(tblContact)

            } else {
                val tblContact: TblContact = TblContact(
                    ContID = resultset.getInt("ContID"),
                    ContactName = resultset.getString("ContactName"),
                    ContactNumber = resultset.getString("ContactNumber"),
                    IsActive = resultset.getInt("IsActive"),
                    Post = resultset.getString("Post"),
                    MemberType = resultset.getInt("MemberType"),
                    Tenure = resultset.getString("Tenure"),
                    Address = resultset.getString("Address"),
                    Image = null

                )
                membersList.add(tblContact)

            }

        }


        resultset2 = stmt.executeQuery(query)
        while (resultset2.next()) {
            val tblBoardMemberType: TblBoardMemberType = TblBoardMemberType(
                MemTypeID = resultset2.getInt("MemTypeID"),
                MemberType = resultset2.getString("MemberType"),
                isOldMember = resultset2.getInt("isOldMember"),
            )
            memberTypeList.add(tblBoardMemberType)

        }


        conn.close()

        return MembersListResponse(
            tblContact = membersList,
            tblBoardMemberType = memberTypeList,
        )
    }

    fun getNotices(context: Context): NoticesListResponse? {

        var stmt: Statement? = null
        var resultset: ResultSet? = null

        val query = "select * from tblNotice where IsActive=1"

        val ss = SqlServerFunctions()
        val conn: Connection = ss.ConnectToSQLServer(prefs)
        stmt = conn.createStatement()


        var noticesList: MutableList<TblNotice> = arrayListOf()




        resultset = stmt.executeQuery(query)
        while (resultset.next()) {


            if (resultset.getBytes("NoticeFile") != null) {
                val data: ByteArray = resultset.getBytes("NoticeFile")
                val imageStream = ByteArrayInputStream(data)
                val theImage = BitmapFactory.decodeStream(imageStream)
                val image = File(
                    context.getExternalFilesDir(null),
                    "noticeImage" + resultset.getInt("NoticeID").toString() + ".png"
                )
                val fos = FileOutputStream(image)
                fos.use { theImage.compress(Bitmap.CompressFormat.PNG, 100, it) }


                val notices: TblNotice = TblNotice(
                    NoticeID = resultset.getInt("NoticeID"),
                    NoticeHeadline = resultset.getString("NoticeHeadline"),
                    NoticeDesc = resultset.getString("NoticeDesc"),
                    DateNep = resultset.getString("DateNep"),
                    DateTimeEng = resultset.getString("DateTimeEng"),
                    NoticeFile = Uri.parse(image.path).toString()

                )
                noticesList.add(notices)
            } else {

                val notices: TblNotice = TblNotice(
                    NoticeID = resultset.getInt("NoticeID"),
                    NoticeHeadline = resultset.getString("NoticeHeadline"),
                    NoticeDesc = resultset.getString("NoticeDesc"),
                    DateNep = resultset.getString("DateNep"),
                    DateTimeEng = resultset.getString("DateTimeEng"),
                    NoticeFile = null
                )
                noticesList.add(notices)

            }
        }


        conn.close()

        return NoticesListResponse(
            tblNotice = noticesList
        )
    }

    fun getAboutOrg(context: Context): AboutOrgResponse? {

        var stmt: Statement? = null
        var resultset: ResultSet? = null

        val query = "select * from TblAboutOrg where Cont_id=1"

        val ss = SqlServerFunctions()
        val conn: Connection = ss.ConnectToSQLServer(prefs)
        stmt = conn.createStatement()

        var aboutOrg: TblAboutOrg? = null

        resultset = stmt.executeQuery(query)
        while (resultset.next()) {
            if (resultset.getBytes("Cont_image") != null) {
                val data: ByteArray = resultset.getBytes("Cont_image")
                val imageStream = ByteArrayInputStream(data)
                val theImage = BitmapFactory.decodeStream(imageStream)
                val image = File(
                    context.getExternalFilesDir(null),
                    "aboutOrgImage.png"
                )
                val fos = FileOutputStream(image)
                fos.use { theImage.compress(Bitmap.CompressFormat.PNG, 100, it) }


                val about: TblAboutOrg = TblAboutOrg(
                    Cont_id = resultset.getInt("Cont_id"),
                    Cont_details = resultset.getString("Cont_details"),
                    Cont_image = Uri.parse(image.path).toString()
                )
                aboutOrg = about
            } else {
                val about: TblAboutOrg = TblAboutOrg(
                    Cont_id = resultset.getInt("Cont_id"),
                    Cont_details = resultset.getString("Cont_details"),
                    Cont_image = null
                )
                aboutOrg = about


            }
        }
        conn.close()

        return aboutOrg?.let {
            AboutOrgResponse(
                tblAbout = it
            )
        }


    }

    fun getContactList(context: Context): ContactsListResponse? {

        var stmt: Statement? = null
        var resultset: ResultSet? = null

        val query = "select * from TblDepartmentContact"

        val ss = SqlServerFunctions()
        val conn: Connection = ss.ConnectToSQLServer(prefs)
        stmt = conn.createStatement()


        var contactsList: MutableList<TblDepartmentContact> = arrayListOf()

        resultset = stmt.executeQuery(query)
        while (resultset.next()) {

            val contacts: TblDepartmentContact = TblDepartmentContact(
                Dept_id = resultset.getInt("Dept_id"),
                Dept_name = resultset.getString("Dept_name").orEmpty(),
                Dept_contact = resultset.getString("Dept_contact").orEmpty(),
                Dept_mail = resultset.getString("Dept_mail").orEmpty(),

                )
            contactsList.add(contacts)

        }

        conn.close()

        return ContactsListResponse(
            tblDepartmentContact = contactsList
        )
    }

    fun getRequiredFiles(context: Context): DocumentTypesResponse? {

        var stmt: Statement? = null
        var resultset: ResultSet? = null

        val query = "select * from tblDocumentType"

        val ss = SqlServerFunctions()
        val conn: Connection = ss.ConnectToSQLServer(prefs)
        stmt = conn.createStatement()

        var contactsList: MutableList<RegistrationRequest.RequiredDocuments> = arrayListOf()

        resultset = stmt.executeQuery(query)
        while (resultset.next()) {
            val contacts: RegistrationRequest.RequiredDocuments =
                RegistrationRequest.RequiredDocuments(
                    DocumentName = resultset.getString("DocTypeName"),
                    DocImage = null,
                )
            contactsList.add(contacts)
        }
        conn.close()

        return DocumentTypesResponse(
            documentTypes = contactsList
        )
    }

    fun requestForReg(data: RegistrationRequest?, context: Context): String? {
        var stmt: Statement? = null
        var maxId: Int = 0
        val ss = SqlServerFunctions()
        val conn: Connection = ss.ConnectToSQLServer(prefs)
        stmt = conn.createStatement()
        try {

            var rs: ResultSet =
                stmt.executeQuery("Select isnull(Max(ReqID),0) as ReqID from tblOnlineTapRequest")
            while (rs.next()) {
                maxId = Integer.parseInt(rs.getString("ReqID")) + 1
            }
        } catch (e: Exception) {
            Log.e("DBTest", "Unable Connect to Server", e)
        }

        val query1 =
            "INSERT INTO [DropcareTrial].[dbo].[tblOnlineTapRequest] (ReqID,MemName,Address,Gender,CitNo,ContactNo,FHName,GFILName,MaleCount,FemaleCount) " +
                    "VALUES ($maxId,'${data?.MemName.toString()}', '${
                        data?.Address.toString()
                    }', '${data?.Gender.toString()}', '${
                        data?.CitNo.toString()
                    }', '${data?.ContactNo.toString()}'," +
                    "'${data?.FHName.toString()}', '${
                        data?.GFILName.toString()
                    }', ${data?.MaleCount}, ${data?.FemaleCount} );"


        for (files in data?.files.orEmpty()) {
            var path: String
            try {
                path = FilePath.getRealPath(context, Uri.parse(files.DocImage)).orEmpty()
            } catch (ex: Exception) {
                val myUri = Uri.parse(files.DocImage)
                path = myUri.encodedPath.toString()
                FilePath.getRealPathFromURI(context, Uri.parse(files.DocImage)).orEmpty()
            }

            val pic = BitmapFactory.decodeFile(path)
            val docImg: ByteArray = getBytes(pic)

            val picsql =
                "Insert into tblOnlineTapReqDocImg([ReqID],[DocumentName],[DocImage]) Values(?,?,?)"
            val pics = conn.prepareStatement(picsql)
            pics.setInt(1, maxId)
            pics.setString(2, files.DocumentName.toString())
            pics.setBytes(3, docImg)
            pics.execute()
        }
        stmt.execute(query1)

        conn.close()

        return "Success"
    }

    private fun getBytes(bitmap: Bitmap): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream)
        return stream.toByteArray()
    }

    fun getBillDetails(context: Context, memberId: Int): BillDetailsResponse? {

        var stmt: Statement? = null
        var resultset: ResultSet? = null

        val query = "SELECT [MemberID],[MemName],[TapNo],[Address],[TapType],[RID]" +
                ",[TotReading],[Amt],[Inv_Date],[Sam_Date],[PaidStatus],[Dis],[Fine]" +
                ",[NetAmt] FROM [tblTempBillDetail] Where Amt>0 and MemberID=" + memberId
            .toString()

        val ss = SqlServerFunctions()
        val conn: Connection = ss.ConnectToSQLServer(prefs)
        stmt = conn.createStatement()


        var billDetailsList: MutableList<BillDetails> = arrayListOf()

        resultset = stmt.executeQuery(query)


        val totalBillDetails: BillDetails = BillDetails(
            999999, null, 0, null, null, null, 0, 0f, null, null, 1, 0f, 0f, 0f
        )

        while (resultset.next()) {

            totalBillDetails.TotReading =
                totalBillDetails.TotReading?.plus(resultset.getInt("TotReading"))
            totalBillDetails.Amt = totalBillDetails.Amt?.plus(resultset.getInt("Amt"))
            totalBillDetails.Dis = totalBillDetails.Dis?.plus(resultset.getInt("Dis"))
            totalBillDetails.Fine = totalBillDetails.Fine?.plus(resultset.getInt("Fine"))
            totalBillDetails.NetAmt = totalBillDetails.NetAmt?.plus(resultset.getInt("NetAmt"))
            totalBillDetails.MemberID = resultset.getInt("MemberID")
            totalBillDetails.MemName = resultset.getString("MemName")
            totalBillDetails.TapNo = resultset.getInt("TapNo")
            totalBillDetails.Address = resultset.getString("Address").orEmpty()
            totalBillDetails.TapType = resultset.getString("TapType").orEmpty()
            totalBillDetails.RID = resultset.getInt("RID")
            totalBillDetails.Inv_Date = resultset.getString("Inv_Date").orEmpty()
            totalBillDetails.Sam_Date = resultset.getString("Sam_Date").orEmpty()

            val billDetails: BillDetails = BillDetails(
                MemberID = resultset.getInt("MemberID"),
                MemName = resultset.getString("MemName").orEmpty(),
                TapNo = resultset.getInt("TapNo"),
                Address = resultset.getString("Address").orEmpty(),
                TapType = resultset.getString("TapType").orEmpty(),
                RID = resultset.getInt("RID"),
                TotReading = resultset.getInt("TotReading"),
                Amt = resultset.getFloat("Amt"),
                Inv_Date = resultset.getString("Inv_Date").orEmpty(),
                Sam_Date = resultset.getString("Sam_Date").orEmpty(),
                Dis = resultset.getFloat("Dis"),
                Fine = resultset.getFloat("Fine"),
                NetAmt = resultset.getFloat("NetAmt"),
                PaidStatus = resultset.getInt("PaidStatus")
            )

            billDetailsList.add(billDetails)
        }

        conn.close()


        if (billDetailsList.isNotEmpty()) {
            billDetailsList.add(totalBillDetails)
        }

        return BillDetailsResponse(
            billDetails = billDetailsList
        )
    }

    fun addTapResponce(context: Context, phoneNo: String, pin: String): UserDetailsResponse? {

        var stmt: Statement? = null


        val UID: String = phoneNo
        val PWD: String = pin

        var tapCount = 0

        val qry =
            "Select * from tblMember where ContactNo='$UID' and PinCode=$PWD"
        var tblMember: MutableList<TblMember> = arrayListOf()

        try {
            val ss = SqlServerFunctions()
            val conn: Connection = ss.ConnectToSQLServer(prefs)
            stmt = conn.createStatement()
            var resultset: ResultSet? = stmt.executeQuery(qry)

            while (resultset!!.next()) {
                tapCount += 1
                val member: TblMember = TblMember(
                    MemberID = resultset.getInt("MemberID"),
                    ContactNo = resultset.getString("ContactNo"),
                    MemName = resultset.getString("MemName"),
                    PinCode = resultset.getString("PinCode"),
                    Address = resultset.getString("Address"),
                    RegDateTime = resultset.getString("RegDateTime")
                )
                tblMember.add(member)
            }
            conn.close()
        }catch (e:Exception){
            return UserDetailsResponse(
                tblMember = null,
                message = "Couldn't connect to server please try again later",
                status = "error"
            )
        }



        if (tapCount==0){
            return UserDetailsResponse(
                tblMember = null,
                message = "Contact or PIN code doesn't match",
                status = "error"
            )
        }

        return UserDetailsResponse(
            tblMember = tblMember,
            message = "success",
            status = "success"
        )
    }





    fun requestPin(phoneNo: String, memberId: String): String? {
        var RC = 0
        var SFRC = 0
        var code = 0
        var stmt: Statement? = null
        var resultset: ResultSet? = null

        var SecCode = ""
        val ss: SqlServerFunctions = SqlServerFunctions()
        val smsfeatquery =
            "Select * from tblHospitalSetting Where SettingName='OTPSMSEnabled' and SettingValue='True'"
        val conn: Connection = ss.ConnectToSQLServer(prefs)
        stmt = conn.createStatement()

        resultset = stmt.executeQuery(smsfeatquery)
        while (resultset.next()) {
            SFRC += 1
        }

        if (SFRC == 0) {
            conn.close()
            return "SMS features is not activated yet"
        }

        val qry = "select * from tblMember where MemberID=" + memberId +
                " and ContactNo='" + phoneNo + "'"

        resultset = stmt.executeQuery(qry)
        while (resultset.next()) {
            RC += 1
        }

        val TokenStr: String = GetFieldData(
            "SettingValue",
            "Select * from tblHospitalSetting Where SettingName='SMSTokenValue'", stmt
        )
        val ShortStr: String = GetFieldData(
            "SettingValue",
            "Select * from tblHospitalSetting Where SettingName='SMSShortName'", stmt
        )

        if (RC > 0) {
            try {
                val d = Calendar.getInstance().time
                val df1 = SimpleDateFormat("smd") //second minute date
                SecCode = df1.format(d)
                val url = URL("https://api.sparrowsms.com/v2/sms/")
                val conn = url.openConnection() as HttpURLConnection
                conn.doOutput = true
                conn.requestMethod = "POST"
                conn.setRequestProperty("Content-Type", "application/json")
                val input =
                    "{\"token\":\"" + TokenStr + "\",\"from\":\"" + ShortStr + "\",\"to\":\"" + phoneNo +
                            "\",\"text\":\"Your Access Code for Dropcare is: " + SecCode + "\"}"
                val os = conn.outputStream
                os.write(input.toByteArray())
                os.flush()
                code = conn.responseCode
                conn.disconnect()
            } catch (e: MalformedURLException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            if (code == 200) {
                try {
                    val MN: String = phoneNo
                    val MMID: Int = memberId.toInt()
                    val SC = SecCode.toInt()
                    UpdateSecurityCode(MN, MMID, SC, stmt)
                    conn.commit()
                } catch (ex: Exception) {
                    Timber.i("SQL Exception Occured")
                }
                conn.close()

                return "Access Code is sent to your mobile"

            } else {
                conn.close()

                return "SMS Connection Server Error"
            }
        } else {

            conn.close()

            return "Either Mobile or MemberID is not registered"

        }

    }

    private fun GetFieldData(FieldName: String, qry: String, stmt: Statement?): String {
        val rs = stmt!!.executeQuery(qry)
        var RetVal: String = "0"
        while (rs.next()) {
            RetVal = rs.getString(FieldName)
        }
        return RetVal
    }


    fun UpdateSecurityCode(mn: String, mmid: Int, sc: Int, stmt: Statement) {
        val qry =
            "Update tblMember Set PinCode=$sc where MemberID=$mmid and ContactNo='$mn'"
        stmt.executeUpdate(qry)
    }

    fun changePin(phoneNo: String?, memberId: String?, newPin: String): String? {
        var stmt: Statement? = null
        val ss = SqlServerFunctions()
        val conn: Connection = ss.ConnectToSQLServer(prefs)
        stmt = conn.createStatement()
        return try {
            UpdateSecurityCode(
                phoneNo.toString(),
                memberId.toString().toInt(),
                newPin.toInt(),
                stmt
            )
            conn.close()
            "Success"
        } catch (a: Exception) {
            conn.close()
            "Error"
        }


    }

    fun addComplaint(message: String, memberID: String?, phoneNo: String?): String {
        var stmt: Statement? = null
        val ss = SqlServerFunctions()
        val conn: Connection = ss.ConnectToSQLServer(prefs)
        stmt = conn.createStatement()
        return try {
            val qry =
                "Insert into [tblComplaint]([MemberID],[ComplaintMsg],[ContactNumber]) Values(" + memberID + ",'" +
                        message + "','" + phoneNo + "')"
            stmt.execute(qry)
            conn.close()
            "Success"
        } catch (a: Exception) {
            conn.close()
            "Error"
        }
    }

    fun getComplaintList(memberID: String?, phoneNo: String?): MutableList<ComplaintResponse>? {
        var stmt: Statement? = null
        val ss = SqlServerFunctions()
        val conn: Connection = ss.ConnectToSQLServer(prefs)
        stmt = conn.createStatement()

        var complaintList: MutableList<ComplaintResponse> = arrayListOf()

        return try {
            var resultset: ResultSet? = null

            val qry =
                "SELECT TOP 100 * " +
                        "FROM tblComplaint " +
                        "where MemberID=${memberID?.toInt()} and ContactNumber='$phoneNo' "
//                        "ORDER BY ComptID DESC "
            resultset = stmt.executeQuery(qry)

            while (resultset.next()) {
                val list: ComplaintResponse = ComplaintResponse(
                    ComptID = resultset.getInt("ComptID"),
                    MemberID = resultset.getInt("MemberID"),
                    ComplaintMsg = resultset.getString("ComplaintMsg"),
                    ComptDate = resultset.getString("ComptDate"),
                    IsRectified = resultset.getInt("IsRectified")
                )
                complaintList.add(list)
            }
            conn.close()
            complaintList
        } catch (a: Exception) {
            conn.close()
            complaintList
        }
    }
}


