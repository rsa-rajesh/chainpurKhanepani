package com.heartsun.pithuwakhanipani.data.repository

import android.R.attr
import com.heartsun.pithuwakhanipani.data.Prefs
import com.heartsun.pithuwakhanipani.domain.dbmodel.*
import com.heartsun.pithuwakhanipani.utils.connectionUtils.SqlServerFunctions
import java.sql.Connection
import java.sql.ResultSet
import java.sql.Statement
import android.R.attr.data
import android.content.Context

import java.io.ByteArrayInputStream
import android.graphics.BitmapFactory

import android.graphics.Bitmap
import android.net.Uri
import java.io.FileOutputStream

import java.io.File

import android.os.Environment
import com.heartsun.pithuwakhanipani.domain.*


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

//
//            val data: ByteArray = resultset.getBytes("Photo")
//            val imageStream = ByteArrayInputStream(data)
//            val theImage = BitmapFactory.decodeStream(imageStream)
//            val image = File(context.getExternalFilesDir(null), "memberImage" + resultset.getInt("ContID").toString() + ".png")
//            val fos = FileOutputStream(image)
//            fos.use { theImage.compress(Bitmap.CompressFormat.PNG, 100, it) }

//            val tblContact: TblContact = TblContact(
//                ContID = resultset.getInt("ContID"),
//                ContactName = resultset.getString("ContactName"),
//                ContactNumber = resultset.getString("ContactNumber"),
//                IsActive = resultset.getInt("IsActive"),
//                Post = resultset.getString("Post"),
//                MemberType = resultset.getInt("MemberType"),
//                Tenure = resultset.getString("Tenure"),
//                Address = resultset.getString("Address"),
//                Image =
//            )
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
//                    NoticeFile = resultset.getString("NoticeFile")
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
                aboutOrg= about
            } else {
                val about: TblAboutOrg = TblAboutOrg(
                    Cont_id = resultset.getInt("Cont_id"),
                    Cont_details = resultset.getString("Cont_details"),
                    Cont_image =null
                )
                aboutOrg= about


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
            val contacts: RegistrationRequest.RequiredDocuments = RegistrationRequest.RequiredDocuments(
                DocumentName = resultset.getString("DocTypeName"),
                DocImage =null,
                )
            contactsList.add(contacts)
        }
        conn.close()

        return  DocumentTypesResponse(
            documentTypes = contactsList
        )
    }
}


