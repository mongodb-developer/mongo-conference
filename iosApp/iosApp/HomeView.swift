//
//  HomeView.swift
//  iosApp
//
//  Created by Mohit Sharma on 17.10.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import shared

struct HomeView: View {

    @State var conferenceList = [ConferenceInfo]()
    var repo = RealmRepo()

    var body: some View {
        VStack {
            
            List(conferenceList, id: \.self.id) { info in
                HStack {
                        VStack(alignment: .leading) {
                            Text(info.name)
                        }
                    
                        Spacer()
                }
            }
        }
        .onAppear {
            getConferenceList()
        }
        .navigationBarBackButtonHidden(true)
    }


    func getConferenceList() {
        print("Called")
        Task {
            do {
                try await repo.getEventLists().watch(block: { items in
                    conferenceList = items as! [ConferenceInfo]
                })
                
            } catch {
                print("Unexpected: \(error)")
            }
        }
    }

}

struct HomeView_Previews: PreviewProvider {
    static var previews: some View {
        HomeView()
    }
}
