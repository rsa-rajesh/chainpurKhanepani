package com.heartsun.pithuwakhanipani.data.repository

import com.heartsun.pithuwakhanipani.data.Prefs
import com.heartsun.pithuwakhanipani.domain.MembersListResponse
import com.heartsun.pithuwakhanipani.domain.WaterRateListResponse
import com.heartsun.pithuwakhanipani.domain.dbmodel.*
import com.heartsun.pithuwakhanipani.utils.connectionUtils.SqlServerFunctions
import java.sql.Connection
import java.sql.ResultSet
import java.sql.Statement

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

    fun getMembers(): MembersListResponse {
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

            val tblContact: TblContact = TblContact(
                ContID = resultset.getInt("ContID"),
                ContactName = resultset.getString("ContactName"),
                ContactNumber = resultset.getString("ContactNumber"),
                IsActive = resultset.getInt("IsActive"),
                Post = resultset.getString("Post"),
                MemberType = resultset.getInt("MemberType"),
                Tenure = resultset.getString("Tenure")

            )
            membersList.add(tblContact)
        }


        resultset2 = stmt.executeQuery(query)
        while (resultset2.next()) {
            val tblBoardMemberType: TblBoardMemberType = TblBoardMemberType(
                MemTypeID = resultset2.getInt("MemTypeID"),
                MemberType = resultset2.getString("MemberType"),

            )
            memberTypeList.add(tblBoardMemberType)

        }


        conn.close()

        return MembersListResponse(
            tblContact = membersList,
            tblBoardMemberType = memberTypeList,
        )
    }
}


